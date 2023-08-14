package game.racers.land;

import game.racers.Racer;
import utilities.EnumContainer;


public class Horse extends Racer implements LandRacer {
	private static final String CLASS_NAME = "Horse";
	private static final double DEFAULT_MAX_SPEED = 50;
	private static final double DEFAULT_ACCELERATION = 2;
	private static final EnumContainer.Color DEFAULT_color = EnumContainer.Color.BLACK;
	private EnumContainer.Breed breed;
	
	/**
	 * Default Horse constructor using default settings
	 */
	public Horse() {
		super("", DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
		setBreed(EnumContainer.Breed.THOROUGHBRED);
	}
	
	/**
	 * User defined Horse constructor, using user and default settings
	 * @param name {@code String}
	 * @param maxSpeed {@code double}
	 * @param acceleration {@code double}
	 * @param color {@code EnumContainer.Color}
	 */
	public Horse(String name, double maxSpeed, double acceleration, EnumContainer.Color color) {
		super(name, maxSpeed, acceleration, color);
		setBreed(EnumContainer.Breed.THOROUGHBRED);
	}
	
	public Horse(Horse other) {
		super(other.getName(), other.getMaxSpeed(), other.getAcceleration(), other.getColor());
	}
	
	/**
	 * @return Horse's breed as EnumContainer.Breed
	 */
	public EnumContainer.Breed getBreed() { return this.breed; }
	/**
	 * Sets horse's breed
	 * @param breed {@code EnumContainer.Breed}
	 * @return True if set
	 */
	private boolean setBreed(EnumContainer.Breed breed) {
		this.breed = breed;
		return true;
	}
	
	public String describeSpecific() {
		return String.format("Breed: %s", this.breed);
	}
	
	public String className() {
		return CLASS_NAME;
	}
}
