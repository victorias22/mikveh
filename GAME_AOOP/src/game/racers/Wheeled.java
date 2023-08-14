package game.racers;
public class Wheeled {
	private int numOfWheels;
	
	/**
	 * Wheeled constructor
	 * @param numOfWheels {@code int}
	 */
	public Wheeled(int numOfWheels) {
		setNumOfWheels(numOfWheels);
	}
	
	/**
	 * @return Number of wheels for racer as int
	 */
	public int getNumOfWheels() { return this.numOfWheels; }
	
	/**
	 * Sets number of wheels for racer
	 * @param numOfWheels {@code int}
	 * @return True if set, else False
	 */
	private boolean setNumOfWheels(int numOfWheels) {
		if(numOfWheels <= 0) {
			this.numOfWheels = 0;
			return false;
		}
		this.numOfWheels = numOfWheels;
		return true;
	}
}
