package game.window;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.ImageIO;
import javax.swing.*;

import factory.*;
import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import game.racers.land.Car;
import utilities.EnumContainer;

public class GameWindow extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	//Arena select components and fields
	private JLabel chsArena, arenaLength, maxRacers;
	private JTextField lenField, maxField;
	private JComboBox<String> arenas;
	private JButton buildArena, buildDefaultRace;
	private static Arena activeArena;
	private int choice;
	private String selectedArena = "game.arenas.air." + ARENAS[0];
	private final static String[] ARENAS = { "AerialArena", "NavalArena", "LandArena" };
	
	//Racer builder component
	private JLabel chsRacer, chsColor, racerName, maxSpeed, accel, prototypes;
	private JTextField nameField, speedField, accelField;
	private JComboBox<String> racers, colors;
	private JComboBox<Object> prototypeRacers;
	private JButton addRacer;
	private String racerType = "air";
	private final static String[] RCRTYPE = {"Airplane", "Helicopter", "Car", "Bicycle", "Horse", "RowBoat", "SpeedBoat"};
	private final static String[] COLORS = {"Black", "Red", "Green", "Blue", "Yellow"};
	
	//Game component
	private final static String path = "/game/window/icons/";
	private GamePanel game;
	private static boolean arenaBuilt = false;
	
	//Main window frame components
	private JPanel arenaSelect, mainPanel, sidebar, racerBuilder;
	private GameControl gameControl;
	private static RaceBuilder builder = RaceBuilder.getInstance();
	private KeyAdapter onlyNums;
	
	/**
     * Constructs a GameWindow object with the specified title.
     *
     * @param title the title of the game window
     */
	public GameWindow(String title){
		super(title);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		
		onlyNums = new KeyAdapter() {
	         public void keyPressed(KeyEvent ke) {
	        	 JTextField source = (JTextField)ke.getSource();
	             if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyChar() == 8) {
	            	 source.setEditable(true);
	             } else {
	            	 source.setEditable(false);
	             }
	          }
	       };
		
		ArenaSelect();
		RacerBuilder();
		
		game = new GamePanel();
		
		sidebar = new JPanel();
		GridBagConstraints c = new GridBagConstraints();
		sidebar.setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		sidebar.add(arenaSelect, c);
		
		c.gridy = 1;
		sidebar.add(racerBuilder, c);
		
		gameControl = new GameControl();
		
		c.gridy = 2;
		sidebar.add(gameControl, c);
		
		sidebar.setPreferredSize(new Dimension(150,750));
		
		mainPanel.add(game);
		mainPanel.add(sidebar, BorderLayout.EAST);
		
		add(mainPanel);
		
		setResizable(false);
		pack();
	}
	
	/**
     * Sets up the components for arena selection.
     */
	private void ArenaSelect() {
		arenaSelect = new JPanel();
		
		arenaSelect.setLayout(new GridLayout(0, 1, 0, 5));
		arenaSelect.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.cyan), 
				BorderFactory.createEmptyBorder(10,5,10,5)));
		
		//Creating labels
		chsArena = new JLabel("Choose arena:");
		arenaLength = new JLabel("Arena length:");
		maxRacers = new JLabel("Max racers number:");
		
		//Creating text fields    
		lenField = new JTextField("1000", 10);
		lenField.addKeyListener(onlyNums);
		
		maxField = new JTextField("8", 10);
		maxField.addKeyListener(onlyNums);
		
		//Creating combo box for arenas
		arenas = new JComboBox<String>(ARENAS);
		arenas.addActionListener(this);
		
		//Creating build arena button
		buildArena = new JButton("Build arena");
		buildArena.addActionListener(this);
		
		//Creating build default race button
		buildDefaultRace = new JButton("Build default race");
		buildDefaultRace.addActionListener(this);
		
		arenaSelect.add(chsArena);
		arenaSelect.add(arenas);
		arenaSelect.add(arenaLength);
		arenaSelect.add(lenField);
		arenaSelect.add(maxRacers);
		arenaSelect.add(maxField);
		arenaSelect.add(buildArena);
		arenaSelect.add(buildDefaultRace);
	}
	
	/**
     * Sets up the components for racer builder.
     */
	private void RacerBuilder() {
		racerBuilder = new JPanel();
		
		racerBuilder.setLayout(new GridLayout(0,1,0,5));
		racerBuilder.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.cyan), 
				BorderFactory.createEmptyBorder(10,5,10,5)));
		
		//Creating labels
		chsRacer = new JLabel("Choose racer:");
		chsColor = new JLabel("Choose color:");
		racerName = new JLabel("Racer name:");
		maxSpeed = new JLabel("Max speed:");
		accel = new JLabel("Acceleration:");
		prototypes = new JLabel("Copy existing racer:");
		
		//Creating text fields
		nameField = new JTextField(10);
		
		speedField = new JTextField("0", 10);
		speedField.addKeyListener(onlyNums);
		
		accelField = new JTextField("0", 10);
		accelField.addKeyListener(onlyNums);
		
		//Creating combo boxes for racers and colors
		racers = new JComboBox<String>(RCRTYPE);
		racers.addActionListener(this);
		
		colors = new JComboBox<String>(COLORS);
		
		prototypeRacers = new JComboBox<Object>();
		prototypeRacers.addItem("Create new racer");
		prototypeRacers.setPreferredSize(new Dimension(130, prototypeRacers.getPreferredSize().height));
		prototypeRacers.addActionListener(this);
		
		//Creating add racer button
		addRacer = new JButton("Add racer");
		addRacer.addActionListener(this);
		
		racerBuilder.add(chsRacer);
		racerBuilder.add(racers);
		racerBuilder.add(prototypes);
		racerBuilder.add(prototypeRacers);
		racerBuilder.add(chsColor);
		racerBuilder.add(colors);
		racerBuilder.add(racerName);
		racerBuilder.add(nameField);
		racerBuilder.add(maxSpeed);
		racerBuilder.add(speedField);
		racerBuilder.add(accel);
		racerBuilder.add(accelField);
		racerBuilder.add(addRacer);
	}
	
    /**
     * Handles the action events.
     *
     * @param e the ActionEvent object
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source.equals(arenas)) {
			switch(choice) {
			case 0:
				selectedArena = "game.arenas.air.";
				racerType = "air";
				break;
			case 1:
				selectedArena = "game.arenas.naval.";
				racerType = "naval";
				break;
			case 2:
				selectedArena = "game.arenas.land.";
				racerType = "land";
				break;
			}
			selectedArena += ARENAS[choice];
		}
		
		else if(source.equals(buildArena) && !GameControl.getIsRunning()) {
			choice = arenas.getSelectedIndex();
			int max_Racers = Integer.valueOf(maxField.getText());
			double arenaLength = Double.valueOf(lenField.getText());
			
			if(max_Racers < 1 || max_Racers > 20 || arenaLength < 100 || arenaLength > 3000)
				JOptionPane.showMessageDialog(null, "Invalid input values! Please try again.");
			else
			{
				try {
					activeArena = builder.buildArena(selectedArena, arenaLength, max_Racers);
					arenaSelected(max_Racers);
					GameControl.setGameStarted(false);
					GameControl.setRunning(false);
					mainPanel.setPreferredSize(new Dimension((int)arenaLength + sidebar.getWidth() + 80, Math.max(750, Arena.get_MIN_Y_GAP() * max_Racers - 100)));
					this.pack();
					
					int itemCount = prototypeRacers.getItemCount();
					for(int i = itemCount - 1; i > 0; i--)
						prototypeRacers.removeItemAt(i);
				}
				catch(ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
						| IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		}
		
		else if(source.equals(addRacer)) {
			
			if(activeArena != null && !GameControl.getGameStarted()) {
				if(prototypeRacers.getSelectedIndex() != 0) {
					Racer racer = (Racer)prototypeRacers.getSelectedItem();
					Racer copyRacer = racer.clone();
					String color = colors.getSelectedItem().toString().toUpperCase();
					String name = nameField.getText();
					copyRacer.upgrade(EnumContainer.Color.valueOf(color), name);
					try {
						activeArena.addRacer(copyRacer);
						activeArena.initRace();
						activeArena.addRacerData(copyRacer);
						racerIcon icon = new racerIcon(racer.className(), colors.getSelectedItem().toString(), racer, game.getWidth());
						game.add(icon);
						game.revalidate();
					} catch(RacerLimitException | RacerTypeException e2) {
						if(e2 instanceof RacerLimitException)
							JOptionPane.showMessageDialog(null, "Arena is full! can't add new racers!");
						else if(e2 instanceof RacerTypeException)
							JOptionPane.showMessageDialog(null, "Racer type does not belong to this arena!");
					}
					return;
					
				}
				String name = nameField.getText();
				double max_Speed = Double.valueOf(speedField.getText());
				double acc = Double.valueOf(accelField.getText());
				String type = racers.getItemAt(racers.getSelectedIndex());
				String racer_Type = String.format("game.racers.%s.%s", racerType, type);
				String color = colors.getItemAt(colors.getSelectedIndex());
				Racer racer = null;
				
				if(name.equals("") || max_Speed <= 0 || acc <= 0)
					JOptionPane.showMessageDialog(null, "Invalid input values! Please try again.");
				else {
					try {
					if(type.equals("Airplane") || type.equals("Car") || type.equals("Bicycle"))
						racer = builder.buildWheeledRacer(racer_Type, name, max_Speed, acc, EnumContainer.Color.valueOf(color.toUpperCase()), 0);
					else
						racer = builder.buildRacer(racer_Type, name, max_Speed, acc, EnumContainer.Color.valueOf(color.toUpperCase()));
					} catch(ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
							| IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
						e1.printStackTrace();
						return;
					}
					
					try {
						activeArena.addRacer(racer);
						activeArena.initRace();
						activeArena.addRacerData(racer);
						racerIcon icon = new racerIcon(type, color, racer, game.getWidth());
						game.add(icon);
						game.revalidate();
						prototypeRacers.addItem(racer);
					} catch(RacerLimitException | RacerTypeException e2) {
						if(e2 instanceof RacerLimitException)
							JOptionPane.showMessageDialog(null, "Arena is full! can't add new racers!");
						else if(e2 instanceof RacerTypeException)
							JOptionPane.showMessageDialog(null, "Racer type does not belong to this arena!");
					}
				}
			}
			else
				JOptionPane.showMessageDialog(null, "Please build an arena first!");
		}
		
		else if(source.equals(prototypeRacers)) {
			int selection = prototypeRacers.getSelectedIndex();
			if(selection == 0) {
				nameField.setEnabled(true);
				speedField.setEnabled(true);
				accelField.setEnabled(true);
				racers.setEnabled(true);
				nameField.setText("");
				speedField.setText("0");
				accelField.setText("0");
			}
			else {
				Racer selectedRacer = (Racer)prototypeRacers.getSelectedItem();
				speedField.setEnabled(false);
				accelField.setEnabled(false);
				racers.setEnabled(false);
				nameField.setText(selectedRacer.getName());
				speedField.setText(String.valueOf((int)selectedRacer.getMaxSpeed()));
				accelField.setText(String.valueOf((int)selectedRacer.getAcceleration()));
				racers.setSelectedItem(selectedRacer.className());
				colors.setSelectedItem(selectedRacer.getColor().toString());
			}
		}
		
		else if(source.equals(buildDefaultRace) && !GameControl.getIsRunning()) {
			int max_Racers = Integer.valueOf(maxField.getText());
			
			if(max_Racers < 1 || max_Racers > 20)
				JOptionPane.showMessageDialog(null, "Invalid number of racers");
			else
			{
				try {
					choice = 2;
					activeArena = builder.buildDefaultRace(max_Racers);
					arenaSelected(max_Racers);
					GameControl.setGameStarted(false);
					GameControl.setRunning(false);
					mainPanel.setPreferredSize(new Dimension((int)activeArena.get_length() + sidebar.getWidth() + 80, Math.max(750, Arena.get_MIN_Y_GAP() * max_Racers - 100)));
					this.pack();
					
					int itemCount = prototypeRacers.getItemCount();
					for(int i = itemCount - 1; i > 0; i--)
						prototypeRacers.removeItemAt(i);
					
					Racer racer = new Car();
					activeArena.addRacer(racer);
					
					activeArena.initRace();
					activeArena.addRacerData(racer);
					racerIcon icon = new racerIcon("Car", racer.getColor().toString(), racer, game.getWidth());
					game.add(icon);
					
					for(int i = 1; i < max_Racers; i++) {
						Racer copy = racer.clone();
						copy.setName("");
						activeArena.addRacer(copy);
						activeArena.initRace();
						activeArena.addRacerData(copy);
						icon = new racerIcon("Car", copy.getColor().toString(), copy, game.getWidth());
						game.add(icon);
					}
					
					game.revalidate();
				}
				catch(ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
						| IllegalAccessException | IllegalArgumentException | InvocationTargetException | RacerLimitException | RacerTypeException e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
			
		}
	}
	
	/**
     * Updates the UI after an arena is selected.
     *
     * @param m the maximum number of racers
     */
	public void arenaSelected(int m) {
    	try {
			Image image = ImageIO.read(new File(getClass().getResource(path + ARENAS[choice] + ".jpg").getFile()));
			game.setMaxRacers(m);
			game.setArenaBack(image);
			game.setLength((int)activeArena.get_length());
			game.removeAll();
			game.repaint();
			
			gameControl.setArena(activeArena);
			
			if(!arenaBuilt) {
				arenaBuilt = true;
				Thread gameThread = new Thread(game, "game thread");
				gameThread.setDaemon(true);
				gameThread.start();
			}
		}
    	catch (IOException e) {
			e.printStackTrace();
		}
    }

}