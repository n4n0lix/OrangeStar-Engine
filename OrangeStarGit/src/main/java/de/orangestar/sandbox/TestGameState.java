package de.orangestar.sandbox;

import de.orangestar.engine.logic.GameState;
import de.orangestar.engine.logic.World;


public class TestGameState extends GameState {
    
    @Override
    public void onStateStart() {
        super.onStateStart();
        
        World.Get().addGameObject(new WorldMap());
    }
    
}
