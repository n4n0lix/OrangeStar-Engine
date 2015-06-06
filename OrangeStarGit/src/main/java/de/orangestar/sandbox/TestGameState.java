package de.orangestar.sandbox;

import de.orangestar.engine.logic.GameState;
import de.orangestar.engine.logic.World;


public class TestGameState extends GameState {
    
    @Override
    public void onStateStart() {
        // Add WorldMap to World
        World.Get().addGameObject(new WorldMap());
    }

    @Override
    public void onUpdate() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onStateEnd() {
        // TODO Auto-generated method stub
        
    }
    
}
