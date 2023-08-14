package game.window;

import java.awt.*;
import javax.swing.JPanel;

import game.arenas.Arena;

/**
 * The GamePanel class represents a panel that serves as the game arena.
 * It extends the JPanel class and implements the Runnable interface to allow for rendering and continuous updating of the game state.
 * @author Yovel Nirenberg, Arbel Zagag
 * 
 */
class GamePanel extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	private int length;
	private int maxRacers;
	private Image arenaBack;
	
	/**
     * Constructs a GamePanel object with a preferred size of 1080x700 and initializes the arena background image as null.
     */
	public GamePanel() {
		this.setPreferredSize(new Dimension(1080,700));
		arenaBack = null;
	}
    
	/**
     * Overrides the paintComponent method to paint the game panel.
     *
     * @param g the Graphics context to use for painting
     */
    public void paintComponent(Graphics g) {
    	if(arenaBack != null) {
    		super.paintComponent(g);
    		this.setSize(new Dimension(length, Math.max(750, Arena.get_MIN_Y_GAP() * maxRacers - 100)));
    		Image scaledImage = arenaBack.getScaledInstance(this.getWidth(),this.getHeight(),Image.SCALE_SMOOTH);
			g.drawImage(scaledImage, 0, 0, this);
    	}
	   }
    
    /**
     * Sets the arena background image.
     *
     * @param image the arena background image to set
     * @return true if the image is not null and successfully set, false otherwise
     */
    public boolean setArenaBack(Image image) {
    	if(image != null) {
    		this.arenaBack = image;
    		return true;
    	}
    	return false;
    }
    
    /**
     * Sets the length of the game panel.
     *
     * @param len the length to set
     */
    public void setLength(int len) {
    	this.length = len+80;
    }
    
    /**
     * Sets the maximum number of racers in the game panel.
     *
     * @param m the maximum number of racers to set
     */
    public void setMaxRacers(int m) {
    	this.maxRacers = m;
    }

    /**
     * Overrides the run method from the Runnable interface to continuously repaint the game panel.
     */
	@Override
	public void run() {
		while(true) {
			try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
            GameControl.update();
		}
		
	}
}
