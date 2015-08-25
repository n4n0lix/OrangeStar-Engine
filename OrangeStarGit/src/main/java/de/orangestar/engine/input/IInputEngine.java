package de.orangestar.engine.input;

public interface IInputEngine {
    
    public void         onStart(long windowHandle);
    
    public void         onUpdate();
    
    public void         onShutdown();
    
    public Mouse        getMouse();
    
    public Keyboard     getKeyboard();
}
