package game.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;

import game.racers.Racer;

public class racerIcon extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private String path = "/game/window/icons/";
	private int width, height;
	private Image image;
	private Racer racer;
	private JLabel icon;
	
	/**
     * Constructs a racerIcon object with the specified racer type, color, racer instance, and width.
     *
     * @param racerType the type of the racer
     * @param color     the color of the racer
     * @param racer     the racer instance
     * @param width     the width of the racer icon
     */
	public racerIcon(String racerType, String color, Racer racer, int width) {
		this.width = width;
		this.height = 80;
		this.setOpaque(false);
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.setLayout(new BorderLayout());
		
		try {
			image = ImageIO.read(getClass().getResource(path + racerType + color + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.racer = racer;
		
		image = image.getScaledInstance(this.height, this.height, Image.SCALE_SMOOTH);
        icon = new JLabel(new ImageIcon(image));
        this.add(icon, BorderLayout.LINE_START);
        
		
	}
	
	/**
     * Overrides the paintComponent method to paint the racer icon component.
     *
     * @param g the Graphics context to use for painting
     */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setSize(new Dimension(width, height));
		int x = (int)racer.getCurrentLocation().getX();
		icon.setLocation(x,0);
	}
}
