package game.arenas.naval;

import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import game.racers.naval.NavalRacer;
import utilities.EnumContainer;


public class NavalArena extends Arena {
	private final static double DEFAULT_FRICTION = 0.7;
	private final static int DEFAULT_MAX_RACERS = 5;
	private final static int DEFAULT_LENGTH = 1000;
	
	private EnumContainer.Water water;
	private EnumContainer.WaterSurface surface;
	private EnumContainer.Body body;
	
	/**
	 * Default constructor for naval type arenas, using default settings.
	 */
	public NavalArena() {
		super(DEFAULT_LENGTH, DEFAULT_MAX_RACERS, DEFAULT_FRICTION);
		setWater(EnumContainer.Water.SWEET);
		setSurface(EnumContainer.WaterSurface.FLAT);
		setBody(EnumContainer.Body.LAKE);
	}
	
	/**
	 * Constructor for user defined naval type arenas, using parameters and default settings.
	 * @param length {@code double}
	 * @param maxRacers {@code int}
	 */
	public NavalArena(double length, int maxRacers) {
		super(length, maxRacers, DEFAULT_FRICTION);
		setWater(EnumContainer.Water.SWEET);
		setSurface(EnumContainer.WaterSurface.FLAT);
		setBody(EnumContainer.Body.LAKE);
	}
	/**
	 * Adds new racers to the naval arena
	 */
	public void addRacer(Racer newRacer) throws RacerLimitException, RacerTypeException {
		if(newRacer instanceof NavalRacer) {
			if(super.getActiveRacers().size() == super.get_MAX_RACERS())
				throw new RacerLimitException(String.format("Arena is full! (%d active racers exist). racer #%d was not added", super.get_MAX_RACERS(), newRacer.getSerialNumber()));
			super.set_activeRacers(newRacer);
		}
		else
			throw new RacerTypeException("Invalid Racer of type " + "\"" + newRacer.className() + "\"" + " for Aerial arena");
	}
	
	
	/**
	 * @return Arena's Water settings as EnumContainer.Water
	 */
	public EnumContainer.Water getWater() { return this.water; }
	/**
	 * @return Arena's WaterSurface settings as EnumContainer.WaterSurface
	 */
	public EnumContainer.WaterSurface getSurface() { return this.surface; }
	/**
	 * @return Arena's Body settings as EnumContainer.Body
	 */
	public EnumContainer.Body getBody() { return this.body; }
	
	/**
	 * Sets Arena's Water settings
	 * @param water {@code EnumContainer.Water}
	 */
	private void setWater(EnumContainer.Water water) {
		this.water = water;
	}
	/**
	 * Sets Arena's WaterSurface settings
	 * @param surface {@code EnumContainer.WaterSurface}
	 */
	private void setSurface(EnumContainer.WaterSurface surface) {
		this.surface = surface;
	}
	/**
	 * Sets Arena's Body settings
	 * @param body {@code EnumContainer.Body}
	 */
	private void setBody(EnumContainer.Body body) {
		this.body = body;
	}
}
