package factory;

import java.lang.reflect.InvocationTargetException;

import game.arenas.Arena;
public class ArenaBuilder {
    private String arenaType;
    private double length;
    private int maxRacers;
    private ArenaFactory af = new ArenaFactory();


    public ArenaBuilder setArenaType(String arenaType) {
        this.arenaType = arenaType;
        return this;
    }


    public ArenaBuilder setLength(double length) {
        this.length = length;
        return this;
    }

    public ArenaBuilder setMaxRacers(int maxRacers) {
        this.maxRacers = maxRacers;
        return this;
    }

    public Arena build() 
    		throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        Arena arena = af.getArena(arenaType);
        arena.setLength(length);
        arena.setMaxRacers(maxRacers);
        return arena;
    }
}
