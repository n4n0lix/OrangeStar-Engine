package de.orangestar.engine.logic.component;

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
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Transform _transform;
    private Transform _lastTransform;
}
