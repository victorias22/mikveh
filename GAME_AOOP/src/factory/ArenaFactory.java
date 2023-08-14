package factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import game.arenas.Arena;

public class ArenaFactory {
	private ClassLoader classLoader = ClassLoader.getSystemClassLoader();

	public Arena getArena(String arenaType)
			throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        // Dyn
		Class<?> classObject = this.classLoader.loadClass(arenaType);
		Constructor<?> constructor = classObject.getConstructor();
		
		return (Arena)constructor.newInstance();
    }
}
