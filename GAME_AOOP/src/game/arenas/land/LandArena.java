package game.arenas.land;

import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import game.racers.land.LandRacer;
import utilities.EnumContainer;

public class LandArena extends Arena {
	private final static double DEFAULT_FRICTION = 0.5;
	private final static int DEFAULT_MAX_RACERS = 8;
	private final static int DEFAULT_LENGTH = 800;
	
	private EnumContainer.Coverage coverage;
	private EnumContainer.LandSurface surface;
	
	/**
	 * Default constructor for land type arenas, using default settings.
	 */
	public LandArena() {
		super(DEFAULT_LENGTH, DEFAULT_MAX_RACERS, DEFAULT_FRICTION);
		setCoverage(EnumContainer.Coverage.GRASS);
		setSurface(EnumContainer.LandSurface.FLAT);
	}
	
	/**
	 * Constructor for user defined land type arenas, using parameters and default settings.
	 * @param length {@code double}
	 * @param maxRacers {@code int}
	 */
	public LandArena(double length, int maxRacers) {
		super(length, maxRacers, DEFAULT_FRICTION);
	}
	/**
	 * Adds new racers to the land arena
	 */
	public void addRacer(Racer newRacer) throws RacerLimitException, RacerTypeException {
		if(newRacer instanceof LandRacer) {
			if(super.getActiveRacers().size() == super.get_MAX_RACERS())
				throw new RacerLimitException(String.format("Arena is full! (%d active racers exist). racer #%d was not added", super.get_MAX_RACERS(), newRacer.getSerialNumber()));
			super.set_activeRacers(newRacer);
		}
		else
			throw new RacerTypeException("Invalid Racer of type " + "\"" + newRacer.className() + "\"" + " for Aerial arena");
	}
	
	/**
	 * @return Arena's Coverage settings as EnumContainer.Coverage
	 */
	public EnumContainer.Coverage getCoverage() { return this.coverage; }
	/**
	 * @return Arena's LandSurface settings as EnumContainer.LandSurface
	 */
	public EnumContainer.LandSurface getSurface() { return this.surface; }
	
	/**
	 * Sets Arena's Coverage settings
	 * @param coverage {@code EnumContainer.Coverage}
	 */
	private void setCoverage(EnumContainer.Coverage coverage) {
		this.coverage = coverage;
	}
	/**
	 * Sets Arena's LandSurface settings
	 * @param surface {@code EnumContainer.LandSurface}
	 */
	private void setSurface(EnumContainer.LandSurface surface) {
		this.surface = surface;
	}
}
