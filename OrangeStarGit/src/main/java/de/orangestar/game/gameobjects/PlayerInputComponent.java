package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.input.InputManager;
import de.orangestar.engine.input.Keyboard;
import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.physics.component.UnitPhysicsComponent;

public class PlayerInputComponent extends InputComponent {

    public static float SCROLL_SPEED = 2f;
    
    public PlayerInputComponent(GameObject obj, UnitPhysicsComponent physics) {
    	super(obj);
        _physicsComponent = physics;
    }
    
    @Override
    public void onUpdate() {
        if (_keyboard.W.isDown()) {
            _physicsComponent._velocityY += SCROLL_SPEED;
        }
        
        if (_keyboard.S.isDown()) {
            _physicsComponent._velocityY -= SCROLL_SPEED;
        }
        
        if (_keyboard.A.isDown()) {
            _physicsComponent._velocityX += SCROLL_SPEED;
        }
        
        if (_keyboard.D.isDown()) {
            _physicsComponent._velocityX -= SCROLL_SPEED;
        }
    }
    
    private UnitPhysicsComponent _physicsComponent;
    private Keyboard _keyboard = InputManager.Get().getKeyboard();
    
}
