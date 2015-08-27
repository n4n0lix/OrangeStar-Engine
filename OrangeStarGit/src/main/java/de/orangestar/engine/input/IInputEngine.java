package de.orangestar.engine.input;

import de.orangestar.engine.World;

public interface IInputEngine {
    
    public void         onStart(long windowHandle);
    
    public void         onUpdate();
    
    public void         onShutdown();
    
    public Mouse        getMouse();
    
    public Keyboard     getKeyboard();
    
    public void         addWorld(World world);
    public void         removeWorld(World world);
    
}
