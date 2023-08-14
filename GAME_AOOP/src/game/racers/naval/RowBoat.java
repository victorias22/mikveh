package game.racers.naval;

import game.racers.Racer;
import game.racers.land.Horse;
import utilities.EnumContainer;


public class RowBoat extends Racer implements NavalRacer {
	private static final String CLASS_NAME = "RowBoat";
	private static final double DEFAULT_MAX_SPEED = 75;
	private static final double DEFAULT_ACCELERATION = 10;
	private static final EnumContainer.Color DEFAULT_color = EnumContainer.Color.RED;
	private EnumContainer.BoatType type;
	private EnumContainer.Team team;
	
	/**
	 * Default RowBoat constructor using default settings
	 */
	public RowBoat() {
		super("", DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
		setType(EnumContainer.BoatType.SKULLING);
		setTeam(EnumContainer.Team.DOUBLE);
		
	}
	
	/**
	 * User defined RowBoat constructor, using user and default settings
	 * @param name {@code String}
	 * @param maxSpeed {@code double}
	 * @param acceleration {@code double}
	 * @param color {@code EnumContainer.Color}
	 */
	public RowBoat(String name, double maxSpeed, double acceleration, EnumContainer.Color color){
		super(name, maxSpeed, acceleration, color);
		setType(EnumContainer.BoatType.SKULLING);
		setTeam(EnumContainer.Team.DOUBLE);
	}
	
	public RowBoat(RowBoat other) {
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
		if(type == null) {
			this.type = EnumContainer.BoatType.SKULLING;
			return false;
		}
		this.type = type;
		return true;
	}
	/**
	 * Sets boat team
	 * @param type {@code EnumContainer.Team}
	 * @return True if set
	 */
	private boolean setTeam(EnumContainer.Team team) {
		if(team == null) {
			this.team = EnumContainer.Team.DOUBLE;
			return false;
		}
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
