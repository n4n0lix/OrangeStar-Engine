package de.orangestar.engine.logic;

import de.orangestar.engine.World;

public interface ILogicEngine {
    
    public static final long  TICK_RATE  = 100;
    public static final long  TICK       = TICK_RATE;
    public static final long  TICK_TIME  = 1000 / TICK_RATE;
    public static final float DELTA_TIME = (float) (1d / TICK_RATE);
    
    public void         onStart();
    
    public void         onUpdate();
    
    public void         onShutdown();
    
    public void         addWorld(World world);
    public void         removeWorld(World world);
    
}
