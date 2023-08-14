package game.racers.naval;

import game.racers.Racer;
import utilities.EnumContainer;


public class SpeedBoat extends Racer implements NavalRacer {
	private static final String CLASS_NAME = "SpeedBoat";
	private static final double DEFAULT_MAX_SPEED = 170;
	private static final double DEFAULT_ACCELERATION = 5;
	private static final EnumContainer.Color DEFAULT_color = EnumContainer.Color.RED;
	private EnumContainer.BoatType type;
	private EnumContainer.Team team;
	
	/**
	 * Default SpeedBoat constructor using default settings
	 */
	public SpeedBoat() {
		super("", DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
		setType(EnumContainer.BoatType.SKULLING);
		setTeam(EnumContainer.Team.DOUBLE);
		
	}

	public SpeedBoat(String name, double maxSpeed, double acceleration, EnumContainer.Color color){
		super(name, maxSpeed, acceleration, color);
		setType(EnumContainer.BoatType.SKULLING);
		setTeam(EnumContainer.Team.DOUBLE);
	}
	
	public SpeedBoat(SpeedBoat other) {
		super(other.getName(), other.getMaxSpeed(), other.getAcceleration(), other.getColor());
	}
	
	/**
	 * @return Boat type as EnumContainer.BoatType
	 */
	public EnumContainer.BoatType getType() { return this.type; }
	/**
	 * @return Boat team as EnumContainer.Team
	 */
	public EnumContainer.Team getTeam() { return this.team; }
	
	/**
	 * Sets boat type
	 * @param type {@code EnumContainer.BoatType}
	 * @return True if set
	 */
	private boolean setType(EnumContainer.BoatType type) {
		this.type = type;
		return true;
	}

	private boolean setTeam(EnumContainer.Team team) {
		this.team = team;
		return true;
	}
	
	public String describeSpecific() {
		return String.format("Type: %s, Team: %s", this.type, this.team);
	}
	
	
	public String className() {
		return CLASS_NAME;
	}
}
