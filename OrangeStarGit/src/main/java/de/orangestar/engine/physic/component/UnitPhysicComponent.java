package de.orangestar.engine.physic.component;

import de.orangestar.engine.logic.GameManager;
import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.values.Transform;

public class UnitPhysicComponent extends PhysicComponent {

    public UnitPhysicComponent(UnitLogicComponent logic) {
        _logic = logic;
    }
    
    @Override
    public void onUpdate() {
        Transform unitTransform = _logic.getTransform();
        unitTransform.position.x += _velocityX * GameManager.DELTA_TIME;
        unitTransform.position.y += _velocityY * GameManager.DELTA_TIME;
        unitTransform.position.z += _velocityZ * GameManager.DELTA_TIME;
        
        _velocityX = 0.0f;
        _velocityY = 0.0f;
        _velocityZ = 0.0f;
    }

    private UnitLogicComponent _logic;
    
    public float _velocityX = 0.0f;
    public float _velocityY = 0.0f;
    public float _velocityZ = 0.0f;
    
}
