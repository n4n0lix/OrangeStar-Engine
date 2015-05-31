package de.orangestar.sandbox;

import de.orangestar.engine.logic.GameManager;
import de.orangestar.engine.logic.GameObject;
import de.orangestar.engine.logic.modules.LogicModule;
import de.orangestar.engine.values.Transform;

public class TestModuleLogic extends LogicModule {

    public TestModuleLogic(GameObject parent) {
        super(parent);
    }

    @Override
    public void onUpdate() {
//        Transform transform = getGameObject().getTransform();
//        
//        if (direction) {
//            transform.position.x += speed * GameManager.DELTA_TIME;
//            
//            if (transform.position.x > 0) {
//                direction = !direction;
//            }
//        } else {
//            transform.position.x -= speed * GameManager.DELTA_TIME;
//            
//            if (transform.position.x < -320f) {
//                direction = !direction;
//            }
//        }

    }

    private boolean direction = false;
    private float speed = 16f;
}