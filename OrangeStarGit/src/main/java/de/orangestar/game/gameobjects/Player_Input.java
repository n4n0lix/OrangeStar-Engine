package de.orangestar.game.gameobjects;

import de.orangestar.engine.input.InputManager;
import de.orangestar.engine.input.Keyboard;
import de.orangestar.engine.input.component.InputComponent;

public class Player_Input extends InputComponent {

    public static float SCROLL_SPEED = 64f;
    
    public Player_Input(Player_Logic logic) {
        _logicComponent = logic;
    }
    
    @Override
    public void onUpdate() {
        if (_keyboard.W.isDown()) {
            _logicComponent._velocityY += SCROLL_SPEED;
        }
        
        if (_keyboard.S.isDown()) {
            _logicComponent._velocityY -= SCROLL_SPEED;
        }
        
        if (_keyboard.A.isDown()) {
            _logicComponent._velocityX += SCROLL_SPEED;
        }
        
        if (_keyboard.D.isDown()) {
            _logicComponent._velocityX -= SCROLL_SPEED;
        }
    }
    
    private Player_Logic _logicComponent;
    private Keyboard _keyboard = InputManager.Get().getKeyboard();
    
}
