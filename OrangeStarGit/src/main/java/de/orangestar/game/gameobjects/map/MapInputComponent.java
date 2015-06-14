package de.orangestar.game.gameobjects.map;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.input.InputManager;
import de.orangestar.engine.input.Keyboard;
import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.physics.component.UnitPhysicsComponent;

public class MapInputComponent extends InputComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public static float SCROLL_SPEED = 128f;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public MapInputComponent(GameObject obj, UnitPhysicsComponent physics) {
    	super(obj);
    	
        _keyboard         = InputManager.Get().getKeyboard();
        _physicsComponent = physics;
    }
    
    @Override
    public void onUpdate() {
        float speed = SCROLL_SPEED;
        
        if (_keyboard.ShiftLeft.isDown()) {
            speed *= 4f;
        }
        
        if (_keyboard.W.isDown()) {
            _physicsComponent._velocityY += speed;
        }
        
        if (_keyboard.S.isDown()) {
            _physicsComponent._velocityY -= speed;
        }
        
        if (_keyboard.A.isDown()) {
            _physicsComponent._velocityX += speed;
        }
        
        if (_keyboard.D.isDown()) {
            _physicsComponent._velocityX -= speed;
        }
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Keyboard _keyboard;
    private UnitPhysicsComponent _physicsComponent;

}
