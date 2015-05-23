package de.orangestar.sandbox;

public class MapOptions {
    
    public MapOptions width(int w) {
        _width = w;
        return this;
    }
    
    public MapOptions height(int h) {
        _height = h;
        return this;
    }
    
    public MapOptions withTrees() {
        _trees = true;
        return this;
    }
    
    public MapOptions noTrees() {
        _trees = false;
        return this;
    }
    
    private int     _width  = 16;
    private int     _height = 16;
    
    private boolean _trees = true;
}