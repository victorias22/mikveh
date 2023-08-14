/**
 * 
 */
package utilities;
import javax.swing.JFrame;
import game.window.GameWindow;

public class Program {

	public static void main(String[] args) {
		GameWindow game = new GameWindow("Race");
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.setVisible(true);
		game.setLocationRelativeTo(null);
		

	}

}