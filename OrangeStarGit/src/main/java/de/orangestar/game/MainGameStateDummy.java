package de.orangestar.game;

import de.orangestar.engine.input.InputManager;
import de.orangestar.engine.input.Key.KeyState;
import de.orangestar.engine.input.Keyboard;
import de.orangestar.engine.logic.GameState;
import de.orangestar.engine.logic.World;
import de.orangestar.game.game_objects.Player;
import de.orangestar.game.game_objects.WorldMap;

public class MainGameStateDummy extends GameState {
    
    @Override
    public void onStateStart() {
        // Input
        _keyboard = InputManager.Get().getKeyboard();
        
        // Gameobjects
        World.Get().addGameObject(new Player());
        World.Get().addGameObject(new WorldMap());
    }

    @Override
    public void onUpdate() {
        if (_keyboard.W.getState() == KeyState.PRESSED) {
            System.out.println("Oh my gosh W was pressed!");
        }
    }

    @Override
    public void onStateEnd() {

    }

    
    private Keyboard _keyboard;
        
}
