package factory;
import java.lang.reflect.*;

import game.arenas.Arena;
import game.racers.*;


public class RaceBuilder {
	private static RaceBuilder instance = null;
	private final ArenaBuilder arenaBuilder = new ArenaBuilder();
	private ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	private Class<?> classObject;
	private Constructor<?> constructor;
	
	/**
	 * Default constructor to initialize the Singleton object
	 */
	protected RaceBuilder() {}
	
	/**
	 * @return Singleton instance of the RaceBuilder class
	 */
	public static RaceBuilder getInstance() {
		if(instance == null) {
			instance = new RaceBuilder();
		}
		return instance;
	}

	public Arena buildArena(String arenaType, double length, int maxRacers) 
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
		arenaBuilder.setArenaType(arenaType);
		arenaBuilder.setLength(length);
		arenaBuilder.setMaxRacers(maxRacers);
        return arenaBuilder.build();
	}

	public Arena buildDefaultRace(int maxRacers)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		arenaBuilder.setArenaType("game.arenas.land.LandArena");
		arenaBuilder.setLength(800);
		arenaBuilder.setMaxRacers(maxRacers);
		return arenaBuilder.build();
	}

	public Racer buildRacer(String racerType, String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.classObject = this.classLoader.loadClass(racerType);
		this.constructor = this.classObject.getConstructor(String.class, double.class, double.class, utilities.EnumContainer.Color.class);
		
		return (Racer)this.constructor.newInstance(name, maxSpeed, acceleration, color);
	}

	public Racer buildWheeledRacer(String racerType, String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color, int numOfWheels)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.classObject = this.classLoader.loadClass(racerType);
		this.constructor = this.classObject.getConstructor(String.class, double.class, double.class, utilities.EnumContainer.Color.class, int.class);
		
		return (Racer)this.constructor.newInstance(name, maxSpeed, acceleration, color, numOfWheels);
	}
}
