package de.orangestar.engine.input;

public class DisabledInputEngine implements IInputEngine {

    @Override
    public void onStart(long windowHandle) {
        _mouse = new Mouse();
        _keyboard = new Keyboard();
    }

    @Override
    public void onUpdate() { }

    @Override
    public void onShutdown() { }

    @Override
    public Mouse getMouse() {
        return _mouse;
    }

    @Override
    public Keyboard getKeyboard() {
        return _keyboard;
    }
    
    private Mouse    _mouse;
    private Keyboard _keyboard;

}
