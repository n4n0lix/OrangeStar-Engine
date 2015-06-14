package de.orangestar.game;

import de.orangestar.engine.World;
import de.orangestar.engine.logic.GameState;
import de.orangestar.game.gameobjects.Player;
import de.orangestar.game.gameobjects.map.Map;

public class MainGameStateDummy extends GameState {
    
    @Override
    public void onStateStart() {
        World.Get().addGameObject(map);
    	map.addChild(player);
    	
        World.Get().addGameObject(player);
    }

    @Override
    public void onUpdate() {
        
    }

    @Override
    public void onStateEnd() {

    }

    private Player player = new Player();
    private Map map = new Map();
}
