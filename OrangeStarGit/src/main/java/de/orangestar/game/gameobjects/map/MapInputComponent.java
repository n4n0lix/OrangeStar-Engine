package de.orangestar.game.gameobjects.map;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.input.InputManager;
import de.orangestar.engine.input.Keyboard;
import de.orangestar.engine.input.Mouse;
import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.logic.GameManager;
import de.orangestar.engine.physics.component.UnitPhysicsComponent;
import de.orangestar.engine.render.GLWindow;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Transform;
import de.orangestar.engine.values.Vector3f;

public class MapInputComponent extends InputComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public static float SCROLL_SPEED = 256f;

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
        
        // MOVE MAP
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
    private Mouse _mouse;
    private UnitPhysicsComponent _physicsComponent;

}
