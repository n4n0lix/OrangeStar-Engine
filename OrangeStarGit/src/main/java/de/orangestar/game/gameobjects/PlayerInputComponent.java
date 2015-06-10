package de.orangestar.game.gameobjects;

import de.orangestar.engine.input.InputManager;
import de.orangestar.engine.input.Keyboard;
import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.physic.component.UnitPhysicComponent;

public class PlayerInputComponent extends InputComponent {

    public static float SCROLL_SPEED = 64f;
    
    public PlayerInputComponent(UnitPhysicComponent physic) {
        _physicComponent = physic;
    }
    
    @Override
    public void onUpdate() {
        if (_keyboard.W.isDown()) {
            _physicComponent._velocityY += SCROLL_SPEED;
        }
        
        if (_keyboard.S.isDown()) {
            _physicComponent._velocityY -= SCROLL_SPEED;
        }
        
        if (_keyboard.A.isDown()) {
            _physicComponent._velocityX += SCROLL_SPEED;
        }
        
        if (_keyboard.D.isDown()) {
            _physicComponent._velocityX -= SCROLL_SPEED;
        }
    }
    
    private UnitPhysicComponent _physicComponent;
    private Keyboard _keyboard = InputManager.Get().getKeyboard();
    
}
