package de.orangestar.engine.logic.component;

import de.orangestar.engine.logic.GameManager;
import de.orangestar.engine.values.Transform;

public class UnitLogicComponent extends LogicComponent {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public UnitLogicComponent() {
        _transform = new Transform();
    }

    /**
     * Returns the current transform.
     * @return A transform
     */
    public Transform getTransform() {
        return _transform;
    }
    
    /**
     * Sets the current transform.
     * @param transform A transform
     */
    public void setTransform(Transform transform) {
        _transform = transform;
    }
    
    /**
     * Returns the transform of the last logic tick.
     * @return The last transform
     */
    public Transform getLastTransform() {
        return _lastTransform;
    }

    @Override
    public void onUpdate() {
        _lastTransform = _transform;

        _transform.position.x += _velocityX * GameManager.DELTA_TIME;
        _transform.position.y += _velocityY * GameManager.DELTA_TIME;
        _transform.position.z += _velocityZ * GameManager.DELTA_TIME;
        
        _velocityX = 0.0f;
        _velocityY = 0.0f;
        _velocityZ = 0.0f;
    }
    
    public float _velocityX = 0.0f;
    public float _velocityY = 0.0f;
    public float _velocityZ = 0.0f;

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Transform _transform;
    private Transform _lastTransform;
}
