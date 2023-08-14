package game.racers.air;

import game.racers.*;
import utilities.EnumContainer;

public class Airplane extends Racer implements AerialRacer {
	private static final String CLASS_NAME = "Airplane";
	private static final int DEFAULT_WHEELS = 3;
	private static final double DEFAULT_MAX_SPEED = 885;
	private static final double DEFAULT_ACCELERATION = 100;
	private static final EnumContainer.Color DEFAULT_color = EnumContainer.Color.BLACK;
	private Wheeled wheeled;
	
	/**
	 * Default Airplane constructor using default settings
	 */
	public Airplane() {
		super("", DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
		setWheeled(DEFAULT_WHEELS);
	}

	public Airplane(String name, double maxSpeed, double acceleration, EnumContainer.Color color, int numOfWheels){
		super(name, maxSpeed, acceleration, color);
		setWheeled(DEFAULT_WHEELS);
	}
	
	public Airplane(Airplane other) {
		super(other.getName(), other.getMaxSpeed(), other.getAcceleration(), other.getColor());
		setWheeled(other.getWheeled().getNumOfWheels());
	}

	
	public String describeSpecific() {
		return String.format("Number of Wheels: %d", this.wheeled.getNumOfWheels());
	}
	
	public Wheeled getWheeled() { return this.wheeled;}
	
	public String className() {
		return CLASS_NAME;
	}
	
	private boolean setWheeled(int numOfWheels) {
		if (numOfWheels > 0) {
			this.wheeled = new Wheeled(numOfWheels);
			return true;
		}
		this.wheeled = null;
		return false;
	}
}
