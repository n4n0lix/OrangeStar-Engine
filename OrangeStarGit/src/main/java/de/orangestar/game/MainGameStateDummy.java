package de.orangestar.game;

import de.orangestar.engine.logic.GameState;
import de.orangestar.engine.logic.World;
import de.orangestar.game.game_objects.Player;

public class MainGameStateDummy extends GameState {
    
    @Override
    public void onStateStart() {
        World.Get().addGameObject(new Player());
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onStateEnd() {

    }

}
