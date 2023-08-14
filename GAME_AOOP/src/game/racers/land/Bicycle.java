package game.racers.land;

import game.racers.*;
import game.racers.air.Airplane;
import utilities.EnumContainer;


public class Bicycle extends Racer implements LandRacer {
	private static final String CLASS_NAME = "Bicycle";
	private static final int DEFAULT_WHEELS = 2;
	private static final double DEFAULT_MAX_SPEED = 270;
	private static final double DEFAULT_ACCELERATION = 10;
	private static final EnumContainer.Color DEFAULT_color = EnumContainer.Color.GREEN;
	private EnumContainer.BicycleType type;
	private Wheeled wheeled;
	
	/**
	 * Default Bicycle constructor using default settings
	 */
	public Bicycle() {
		super("", DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
		setWheeled(DEFAULT_WHEELS);
		setType(EnumContainer.BicycleType.MOUNTAIN);
	}
	
	/**
	 * User defined Bicycle constructor, using user and default settings
	 * @param name {@code String}
	 * @param maxSpeed {@code double}
	 * @param acceleration {@code double}
	 * @param color {@code EnumContainer.Color}
	 * @param numOfWheels {@code int}
	 */
	public Bicycle(String name, double maxSpeed, double acceleration, EnumContainer.Color color, int numOfWheels) {
		super(name, maxSpeed, acceleration, color);
		setWheeled(DEFAULT_WHEELS);
		setType(EnumContainer.BicycleType.MOUNTAIN);
	}
	
	public Bicycle(Bicycle other) {
		super(other.getName(), other.getMaxSpeed(), other.getAcceleration(), other.getColor());
		setWheeled(other.getWheeled().getNumOfWheels());
	}
	
	public String describeSpecific() {
		return String.format("Number of Wheels: %d, Bicycle Type: %s", this.wheeled.getNumOfWheels(), this.type);
	}
	
	public String className() {
		return CLASS_NAME;
	}
	
	/**
	 * @return Bicycle type as EnumContainer.BicycleType
	 */
	public EnumContainer.BicycleType getType(){ return this.type; }
	

	private boolean setType(EnumContainer.BicycleType type) {
		this.type = type;
		return true;
	}
	
public Wheeled getWheeled() { return this.wheeled;}
	
	private boolean setWheeled(int numOfWheels) {
		if (numOfWheels > 0) {
			this.wheeled = new Wheeled(numOfWheels);
			return true;
		}
		this.wheeled = null;
		return false;
	}

}
