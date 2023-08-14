package game.racers;
import utilities.*;
import game.arenas.Arena;
import game.racers.states.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public abstract class Racer implements Runnable, Cloneable {
	static int count = 0;
	private int serialNumber;
	private String name;
	private Point currentLocation;
	private Point finish;
	private Arena arena;
	private double maxSpeed;
	private double acceleration;
	private double currentSpeed;
	private double failureProbability;
	private EnumContainer.Color color;
	private Mishap mishap;
	private PropertyChangeSupport support;	
	private RacerState state;
	private boolean broken = false;

	public Racer(String name, double maxSpeed, double acceleration, EnumContainer.Color color) {
		count += 1;
		setSerialNumber(count);
		setName(name);
		setMaxSpeed(maxSpeed);
		setAcceleration(acceleration);
		setColor(color);
		support = new PropertyChangeSupport(this);
		state = new RacerState();
	}
	

	public void addPropertyChangeListener(PropertyChangeListener pcl){ 	
    	support.addPropertyChangeListener(pcl); 
    } 
    

    public void removePropertyChangeListener(PropertyChangeListener pcl){ 	
    	support.removePropertyChangeListener(pcl); 
    } 
	

	public void run() {
		while(currentLocation.getX() != finish.getX()) {
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) {
				System.out.println("Got an exception");
			}
			this.move(arena.get_FRICTION());
			if(broken)
				break;
		}
		synchronized (this) {
			if(!broken) {
				support.firePropertyChange("finished", "No", "Yes");
				this.state.setState(new CompletedState());
				support.firePropertyChange("state", null, this.state);
			}
			else {
				support.firePropertyChange("finished", "No", "Failed");
			}
			arena.crossFinishLine(this);
		}
	}
	

	public void initRace(Arena arena, Point start, Point finish) {
		setFinish(finish);
		setCurrentLocation(start);
		setArena(arena);
	}
	

	public Point move(double friction) {
		double accel = this.acceleration;
		
		RacerAlertState oldState = this.state.getCurrentState();
		//Check for mishaps, if there are none tries to generate one randomly
		if(!hasMishap()) {
			if(Fate.breakDown()) {
				setMishap(Fate.generateMishap());
				System.out.println(String.format("%s Has a new mishap! %s", this.name, this.mishap));
				if(this.mishap.getFixable())
					this.state.setState(new BrokenState());
				else {
					this.state.setState(new DisabledState());
					this.broken = true;
					support.firePropertyChange("state", oldState, this.state.getCurrentState());
					return this.currentLocation;
				}
			}
		}
		
		//Fixes mishap if possible
		if(hasMishap() && mishap.getTurnsToFix() == 0) {
			setMishap(null);
			this.state.setState(new ActiveState());
		}
		else if (hasMishap()) {
			accel = this.acceleration*mishap.getReductionFactor();
			mishap.nextTurn();
		}
		
		//Sets new speed and location for racers
		double newSpeed = this.currentSpeed + accel*friction;
		if (newSpeed >= this.maxSpeed)
			newSpeed = this.maxSpeed;
		
		support.firePropertyChange("currentSpeed", this.currentSpeed, newSpeed); 
		setCurrentSpeed(newSpeed);
		
		Point newX = new Point(this.currentLocation.getX() + this.currentSpeed, 0);
		if(newX.getX() >= finish.getX()) {
			newX = this.finish;
		}
		support.firePropertyChange("xLocation", this.currentLocation.getX(), newX.getX()); 
		setCurrentLocation(newX);
		
		support.firePropertyChange("state", oldState, this.state);
		
		return newX;		
	}
	
	/**
	 * Describes racer's specific details
	 * @return all racer specific details as a String
	 */
	public abstract String describeSpecific();
	/**
	 * @return the name of the specific Racer type class
	 */
	public abstract String className();
	

	public String describeRacer() {
		String details = String.format("Name: %s, SerialNumber: %d, maxSpeed: %.1f, acceleration: %.1f, color: %s", this.name, this.serialNumber, this.maxSpeed, this.acceleration, this.color);
		return details + ", " + describeSpecific();
	}
	

	public boolean hasMishap() {
		if(mishap != null)
			return true;
		return false;
	}
	

	public void introduce() {
		System.out.println(String.format("[%s] ", className()) + describeRacer());
	}
	

	public int getSerialNumber() { return this.serialNumber; }
	/**
	 * @return Racer's name as a String
	 */
	public String getName() { return this.name; }
	/**
	 * @return Racer's location as a Point
	 */
	public Point getCurrentLocation() { return this.currentLocation; }
	/**
	 * @return Finish line as a Point
	 */
	public Point getFinish() { return this.finish; }
	/**
	 * @return Current player's arena as a Arena
	 */
	public Arena getArena() { return this.arena; }
	/**
	 * @return Racer's max speed as a double
	 */
	public double getMaxSpeed() { return this.maxSpeed; }
	/**
	 * @return Racer's acceleration as a double
	 */
	public double getAcceleration() { return this.acceleration; }
	/**
	 * @return Racer's current speed as a double
	 */
	public double getCurrentSpeed() { return this.currentSpeed; }
	/**
	 * @return Racer's failure probability as a double
	 */
	public double getFailureProbability() { return this.failureProbability; }
	/**
	 * @return Racer's color as a EnumContainer.Color
	 */
	public EnumContainer.Color getColor() { return this.color; }
	/**
	 * @return Racer's mishap as a Mishap
	 */
	public Mishap getMishap() { return this.mishap; }
	/**
	 * @return Racer's state as RacerState
	 */
	public RacerState getState() { return this.state; }
	

	private boolean setSerialNumber(int serialNumber) {
		if(serialNumber < count) {
			this.serialNumber = Racer.count;
			return false;
		}
		this.serialNumber = serialNumber;
		return true;
		
	}

	public boolean setName(String name) {
		if(name == "") {
			this.name = className() + " #" + this.serialNumber;
			return false;
		}
		this.name = name;
		return true;
	}

	private boolean setCurrentLocation(Point location) {
		if(location.getX() >= this.finish.getX()) {
			this.currentLocation = new Point(this.finish);
			return false;
		}
		this.currentLocation = new Point(location);
		return true;
	}

	private boolean setFinish(Point finish) {
		if(finish.getX() >= 0) {
			this.finish = new Point(finish);
			return true;
		}
		this.finish = new Point(0,0);
		return false;
	}

	private boolean setArena(Arena arena) {
		if (arena instanceof Arena) {
			this.arena = arena;
			return true;
		}
		return false;
	}
	

	private boolean setMaxSpeed(double maxSpeed) {
		if(maxSpeed >= 0) {
			this.maxSpeed = maxSpeed;
			return true;
		}
		return false;
	}

	private boolean setAcceleration(double acceleration) {
		this.acceleration = acceleration;
		return true;
	}

	private boolean setCurrentSpeed(double currentSpeed) {
		if(currentSpeed >= 0) {
			this.currentSpeed = currentSpeed;
			return true;
		}
		return false;
	}

	private boolean setFailureProbability(double failureProbability) {
		if(failureProbability >= 0) {
			this.failureProbability = failureProbability;
			return true;
		}
		return false;
	}

	private boolean setColor(EnumContainer.Color color) {
		this.color = color;
		return true;
	}

	private boolean setMishap(Mishap mishap) {
		this.mishap = mishap;
		return true;
	}
	
	public boolean isFinished() {
		return this.finish.getX() == this.currentLocation.getX();
	}
	
	public void upgrade(EnumContainer.Color color, String name) {
		this.color = color;
		this.name = name;
	}
	
	public String toString() {
		return String.format("%d - %s - %s", this.serialNumber, this.name, this.className());
	}
	
	public Racer clone() {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		try {
			Class<?> classObject = classLoader.loadClass(this.getClass().getName());
			Constructor<?> constructor = classObject.getConstructor(classObject);
			return (Racer)constructor.newInstance(this);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | 
				InstantiationException | IllegalAccessException | IllegalArgumentException | 
				InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}