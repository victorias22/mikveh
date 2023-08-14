package utilities;

public class Point {
	static final int MAX_X = 1000000;
	static final int MIN_X = 0;
	static final int MAX_Y = 800;
	static final int MIN_Y = 0;
	private double x;
	private double y;
	
	/**
	 * Default constructor sets X point and Y point to 0
	 */
	public Point() {}
	
	/**
	 * User defined X point and Y point constructor
	 * @param x {@code double}
	 * @param y {@code double}
	 */
	public Point(double x, double y) {
		setX(x);
		setY(y);
	}
	
	/**
	 * Copy constructor to copy points
	 * @param other {@code Point}
	 */
	public Point(Point other) {
		this(other.getX(), other.getY());
	}
	
	/**
	 * @return X point as a double
	 */
	public double getX() { return this.x; }
	/**
	 * @return Y point as a double
	 */
	public double getY() { return this.y; }
	
	/**
	 * Sets x point
	 * @param x {@code double}
	 * @return True if set, else False
	 */
	private boolean setX(double x) {
		if(x < MIN_X || x > MAX_X)
			return false;
		this.x = x;
		return true;
	}
	/**
	 * Sets Y point
	 * @param y {@code double}
	 * @return True if set, else False
	 */
	private boolean setY(double y) {
		if(y < MIN_Y || y > MAX_Y)
			return false;
		this.y = y;
		return true;
	}
	
	/**
	 * @return current X point and Y point in the object as (0,0) String
	 */
	public String toString() {
		return "(" + this.x + ", " + this.y + ")";
	}
}
