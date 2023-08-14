package game.racers.air;

import game.racers.Racer;
import game.racers.naval.SpeedBoat;
import utilities.EnumContainer;


public class Helicopter extends Racer implements AerialRacer {
	private static final String CLASS_NAME = "Helicopter";
	private static final double DEFAULT_MAX_SPEED = 400;
	private static final double DEFAULT_ACCELERATION = 50;
	private static final EnumContainer.Color DEFAULT_color = EnumContainer.Color.BLUE;
	
	/**
	 * Default Helicopter constructor using default settings
	 */
	public Helicopter() {
		super("", DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
	}

	public Helicopter(String name, double maxSpeed, double acceleration, EnumContainer.Color color){
		super(name, maxSpeed, acceleration, color);
	}
	
	public Helicopter(Helicopter other) {
		super(other.getName(), other.getMaxSpeed(), other.getAcceleration(), other.getColor());
	}
	
	public String describeSpecific() {
		return "";
	}
	
	
	public String className() {
		return CLASS_NAME;
	}
}
