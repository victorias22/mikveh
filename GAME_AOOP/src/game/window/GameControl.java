package game.window;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.*;

import game.arenas.Arena;
public class GameControl extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	private JButton raceStart, raceInfo;
	private JFrame info;
	private static boolean isRunning = false;
	private static boolean gameStarted = false;
	private static JTable data;
	private final static Vector<String> dataColumns = new Vector<>();
	private Arena arena;
	
	/**
     * Constructs a GameControl object.
     * Sets the layout, border, and creates control buttons.
     */
	public GameControl() {
		setLayout(new GridLayout(0,1,0,10));
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.cyan), 
				BorderFactory.createEmptyBorder(10,5,10,5)));
		
		//Creating control buttons
		raceStart = new JButton("Start race");
		raceStart.addActionListener(this);

		raceInfo = new JButton("Show info");
		raceInfo.addActionListener(this);
		
		add(raceStart);
		add(raceInfo);
		
		dataColumns.add("Racer Name");
		dataColumns.add("Current Speed");
		dataColumns.add("Max Speed");
		dataColumns.add("Current X location");
		dataColumns.add("State");
		dataColumns.add("Finished");
	}

	/**
     * Handles button click events.
     * Starts the race or shows racer information based on the button clicked.
     *
     * @param e the ActionEvent representing the button click event
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == raceStart) {
			if(arena!= null && arena.getActiveRacers().size() > 0 && !isRunning && arena.getCompletedRacers().size() == 0) {
				arena.startRace();
				isRunning = true;
				gameStarted = true;
			}
			else {
				JOptionPane.showMessageDialog(null, "Please build an arena and add racers to it.");
			}
		}
		
		else if(e.getSource() == raceInfo) {
			if(this.arena != null)
				info.setVisible(true);
			else {
				JOptionPane.showMessageDialog(null, "Please build an arena and add racers to it.");
			}
		}
		
	}
	
	/**
     * Sets the arena for the game control panel.
     * Creates a JFrame to display racer information.
     *
     * @param arena the Arena object representing the racing arena
     */
	public void setArena(Arena arena) {
        this.arena = arena;
        info = new JFrame();
		info.setTitle("Racers information");
		data = new JTable(arena.getRacerData(), dataColumns) { 
			private static final long serialVersionUID = 1L;
		  
			public boolean isCellEditable(int row, int column) { return false; }; 
		};
		
		info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		info.add(new JScrollPane(data));
		info.setLocationRelativeTo(this);
		info.pack();
	}
	
	/**
     * Repaints the data table.
     */
	public static void update() {
		data.repaint();
	}
	
	/**
     * Returns the running state of the race.
     *
     * @return true if the race is running, false otherwise
     */
	public static boolean getIsRunning() {
		return isRunning;
	}
	
	/**
     * Returns the game started state.
     *
     * @return true if the game has started, false otherwise
     */

	public static boolean getGameStarted() {
        return gameStarted;
    }
	
	 /**
     * Sets the game started state.
     *
     * @param gameStarted the game started state to set
     */
	public static void setGameStarted(boolean gameStarted) {
        GameControl.gameStarted = gameStarted;
	}
	
	/**
     * Sets the running state of the race.
     *
     * @param isRunning the running state to set
     */
	public static void setRunning(boolean isRunning) {
        GameControl.isRunning = isRunning;
    }
}
