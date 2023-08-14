package game.racers.land;

import game.racers.*;
import utilities.EnumContainer;

public class Car extends Racer implements LandRacer {
	private static final String CLASS_NAME = "Car";
	private static final int DEFAULT_WHEELS = 4;
	private static final double DEFAULT_MAX_SPEED = 400;
	private static final double DEFAULT_ACCELERATION = 20;
	private static final EnumContainer.Color DEFAULT_color = EnumContainer.Color.RED;
	private EnumContainer.Engine engine;
	private Wheeled wheeled;
	
	/**
	 * Default Car constructor using default settings
	 */
	public Car() {
		super("", DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
		setWheeled(DEFAULT_WHEELS);
		setEngine(EnumContainer.Engine.FOURSTROKE);
	}

	public Car(String name, double maxSpeed, double acceleration, EnumContainer.Color color, int numOfWheels) {
		super(name, maxSpeed, acceleration, color);
		setWheeled(DEFAULT_WHEELS);
		setEngine(EnumContainer.Engine.FOURSTROKE);
	}
	
	public Car(Car other) {
		super(other.getName(), other.getMaxSpeed(), other.getAcceleration(), other.getColor());
		setWheeled(other.getWheeled().getNumOfWheels());
	}


	public EnumContainer.Engine getEngine(){ return this.engine; }
	

	private boolean setEngine(EnumContainer.Engine engine) {
		this.engine = engine;
		return true;
	}
	
	public String describeSpecific() {
		return String.format("Number of Wheels: %d, Engine Type: %s", this.wheeled.getNumOfWheels(), this.engine);
	}
	
	
	public String className() {
		return CLASS_NAME;
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
