package game.arenas.air;

import game.arenas.Arena;
import game.racers.Racer;
import game.racers.air.AerialRacer;
import utilities.*;
import game.arenas.exceptions.*;

public class AerialArena extends Arena {
	private final static double DEFAULT_FRICTION = 0.4;
	private final static int DEFAULT_MAX_RACERS = 6;
	private final static int DEFAULT_LENGTH = 1500;
	
	private EnumContainer.Vision vision;
	private EnumContainer.Weather weather;
	private EnumContainer.Height height;
	private EnumContainer.Wind wind;
	
	/**
	 * Default constructor for aerial type arenas, using default settings.
	 */
	public AerialArena() {
		super(DEFAULT_LENGTH, DEFAULT_MAX_RACERS, DEFAULT_FRICTION);
		setVision(EnumContainer.Vision.SUNNY);
		setWeather(EnumContainer.Weather.DRY);
		setHeight(EnumContainer.Height.HIGH);
		setWind(EnumContainer.Wind.HIGH);
	}

	public AerialArena(double length, int maxRacers) {
		super(length, maxRacers, DEFAULT_FRICTION);
		setVision(EnumContainer.Vision.SUNNY);
		setWeather(EnumContainer.Weather.DRY);
		setHeight(EnumContainer.Height.HIGH);
		setWind(EnumContainer.Wind.HIGH);
	}
	/**
	 * Adds new racers to the aerial arena
	 */
	public void addRacer(Racer newRacer) throws RacerLimitException, RacerTypeException {
		if(newRacer instanceof AerialRacer) {
			if(super.getActiveRacers().size() == super.get_MAX_RACERS())
				throw new RacerLimitException(String.format("Arena is full! (%d active racers exist). racer #%d was not added", super.get_MAX_RACERS(), newRacer.getSerialNumber()));
			super.set_activeRacers(newRacer);
		}
		else
			throw new RacerTypeException("Invalid Racer of type " + "\"" + newRacer.className() + "\"" + " for Aerial arena");
	}
	
	/**
	 * @return Arena's vision settings as EnumContainer.Vision
	 */
	public EnumContainer.Vision getVision(){ return this.vision; }
	/**
	 * @return Arena's Weather settings as EnumContainer.Weather
	 */
	public EnumContainer.Weather getWather(){ return this.weather; }
	/**
	 * @return Arena's Height settings as EnumContainer.Height
	 */
	public EnumContainer.Height getHeight(){ return this.height; }
	/**
	 * @return Arena's Wind settings as EnumContainer.Wind
	 */
	public EnumContainer.Wind getWind(){ return this.wind; }
	
	/**
	 * Sets Arena's Vision settings
	 * @param vision {@code EnumContainer.Vision}
	 */
	private void setVision(EnumContainer.Vision vision) {
		this.vision = vision;
	}
	/**
	 * Sets Arena's Weather settings
	 * @param weather {@code EnumContainer.Weather}
	 */
	private void setWeather(EnumContainer.Weather weather) {
		this.weather = weather;
	}
	/**
	 * Sets Arena's Height settings
	 * @param height {@code EnumContainer.Height}
	 */
	private void setHeight(EnumContainer.Height height) {
		this.height = height;
	}
	/**
	 * Sets Arena's Wind settings
	 * @param wind {@code EnumContainer.Wind}
	 */
	private void setWind(EnumContainer.Wind wind) {
		this.wind = wind;
	}
	
}
