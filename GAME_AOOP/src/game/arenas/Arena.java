package game.arenas;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import game.racers.states.BrokenState;
import game.window.GameControl;
import utilities.Point;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JOptionPane;

public abstract class Arena implements PropertyChangeListener, ArenaPlan {
	private Vector<Racer> activeRacers;
	private Vector<Vector<Object>> racerData;
	private ArrayList<Racer> completedRacers;
	private HashMap<Object, Integer> racersIndices;
	private final double FRICTION;
	private int maxRacers;
	private final static int MIN_Y_GAP = 100;
	private double length;
	

	public Arena(double length, int maxRacers, double friction) {
		if(maxRacers >= 0)
			this.maxRacers = maxRacers;
		else {
			this.maxRacers = 0;
			System.out.println("Max racers cannot be negative, setting to 0");
		}
		this.FRICTION = friction;
		if(!set_length(length))
			System.out.println("Arena length cannot be negative, setting to 0");
		
		this.activeRacers = new Vector<>();
		this.completedRacers = new ArrayList<>();
		this.racersIndices = new HashMap<>();
		racerData = new Vector<>();
	}
	

	@Override
	 public synchronized void propertyChange(PropertyChangeEvent evt) {
		String propName = evt.getPropertyName();
		Racer racer = (Racer)evt.getSource();
		
		if(propName.equals("currentSpeed")) {
			racerData.get(racersIndices.get(racer)).set(1, evt.getNewValue());
		}
		else if(propName.equals("xLocation")) {
			racerData.get(racersIndices.get(racer)).set(3, evt.getNewValue());
		}
		else if(propName.equals("finished")) {
			racerData.get(racersIndices.get(racer)).set(5, evt.getNewValue());
			reorderTable();
		}
		else if(propName.equals("state")) {
			System.out.print(racer.getName() + " " + racer.getCurrentLocation().getX() + " meters from start point ");
			racer.getState().alert();
		}
		
		racerData.get(racersIndices.get(racer)).set(0, racer.getName());
		racerData.get(racersIndices.get(racer)).set(2, racer.getMaxSpeed());
		racerData.get(racersIndices.get(racer)).set(4, racer.getState().getCurrentState());
		
	}


	private synchronized void reorderTable() {
		racerData.sort((data1, data2) -> {
            String finished1 = (String) data1.get(5);
            String finished2 = (String) data2.get(5);
            if (finished1.equals("Yes") && (finished2.equals("No") || finished2.equals("Failed"))) {
                return -1; // data1 finished before data2
            } else if ((finished1.equals("No") || finished1.equals("Failed")) && finished2.equals("Yes")) {
                return 1; // data1 finished after data2
            } else {
                return 0; // no change in order
            }
        });
		
		HashMap<Racer, Integer> newRacerIndices = new HashMap<>();
	    for (int i = 0; i < racerData.size(); i++) {
	        Racer racer = findRacerBySerial((Integer) racerData.get(i).get(6));
	        if (racer != null) {
	            newRacerIndices.put(racer, i);
	        }
	    }

	    racersIndices.clear();
	    racersIndices.putAll(newRacerIndices);
	}
	
    /**
     * Finds a racer by their serial number.
     * Searches both the active racers and completed racers to find the racer with the specified serial number.
     *
     * @param serialNumber the serial number of the racer to find
     * @return the racer with the specified serial number, or null if no racer is found
     */
	private Racer findRacerBySerial(int serialNumber) {
        for (Racer racer : activeRacers) {
            if (racer.getSerialNumber() == serialNumber) {
                return racer;
            }
        }
        for (Racer racer : completedRacers) {
            if (racer.getSerialNumber() == serialNumber) {
                return racer;
            }
        }
        return null;
    }
	

	public abstract void addRacer(Racer newRacer) throws RacerLimitException, RacerTypeException;
	

	public void initRace() {
		int i = 0;
		for(Racer r:getActiveRacers()) {
			r.initRace(this, new Point(0, i), new Point(get_length(), i));
			i+=get_MIN_Y_GAP();
			if(r == getActiveRacers().lastElement())
				r.addPropertyChangeListener(this);
		}
	}
	

	public void addRacerData(Racer r) {
		Vector<Object> data = new Vector<>();
		data.add(r.getName());
		data.add(r.getCurrentSpeed());
		data.add(r.getMaxSpeed());
		data.add(r.getCurrentLocation().getX());
		data.add(r.getState().getCurrentState());
		data.add("No");
		data.add(r.getSerialNumber());
		racerData.add(data);
	}
	
	/**
	 * Checks if Arena has any active players left
	 * @return True if there are players left, if no players are left returns False
	 */
	public boolean hasActiveRacers() {
		if(this.activeRacers.size() > this.completedRacers.size())
			return true;
		return false;
	}
	
	/**
	 * Play turn for each player in the Arena until all players are finished
	 */
	public void playTurn() {
		for(Racer r:this.activeRacers) {
			if(this.completedRacers.contains(r)) {
				continue;
			}
			if(r.move(FRICTION).getX() >= this.length)
				crossFinishLine(r);
		}
	}
	
	public void startRace() {
		for(Racer r:this.activeRacers) {
			new Thread(r).start();
		}
	}
	
	/**
	 * Adds players to the finished players array
	 * @param racer
	 */
	public void crossFinishLine(Racer racer) {
		this.completedRacers.add(racer);
		
		if(this.completedRacers.size() == this.activeRacers.size()) {
			GameControl.setRunning(false);
			JOptionPane.showMessageDialog(null, "Race finished!");
		}
		
	}
	
	/**
	 * Shows race results for finished players
	 */
	public void showResults() {
		int i = 0;
		for(Racer r:this.completedRacers) {
			System.out.println("#" + i + " -> " + r.describeRacer());
			i++;
		}
	}
	
	/**
	 * @return Active players as ArrayList<Racer>
	 */
	public Vector<Racer> getActiveRacers(){ return this.activeRacers; }
	/**
	 * @return Finished players as ArrayList<Racer>
	 */
	public ArrayList<Racer> getCompletedRacers() { return this.completedRacers; }
	/**
	 * @return Arena friction as a double
	 */
	public double get_FRICTION() { return this.FRICTION; }
	/**
	 * @return Arena max racers as int
	 */
	public int get_MAX_RACERS() { return this.maxRacers; }
	/**
	 * @return Minimum Y gap between players as int
	 */
	public static int get_MIN_Y_GAP() { return MIN_Y_GAP; }
	/**
	 * @return Arena X length as a double
	 */
	public double get_length() { return this.length; }
	/**
     * @return Racers data as Object[][]
     */
	public Vector<Vector<Object>> getRacerData() { return this.racerData; }
	

	private boolean set_length(double x) {
		if (x >= 0) {
			this.length = x;
			return true;
		}
		this.length = 0;
		return false;
	}
	
	/**
	 * Sets active racers in arena
	 * @param racer {@code Racer}
	 * @return True if racer was added to the arena, else False
	 */
	protected boolean set_activeRacers(Racer racer) {
		for(Racer r:this.activeRacers) {
			if(racer == r)
				return false;
		}
		this.activeRacers.add(racer);
		racersIndices.put(racer, this.activeRacers.size() - 1);
		return true;
	}
	
	public boolean raceFinished() {
		return activeRacers.size() == completedRacers.size();
	}
	
	public void setLength(double length) {
		this.length = length;
	}
	public void setMaxRacers(int maxRacers) {
		this.maxRacers = maxRacers;
	}
	
}
