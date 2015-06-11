package de.orangestar.engine.physics.component;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.values.Vector3f;

public class UnitPhysicsComponent extends PhysicsComponent {

	public UnitPhysicsComponent(GameObject obj) {
		super(obj);
	}

	public float _velocityX;
	public float _velocityY;
	public float _velocityZ;
	
	@Override
	public void onUpdate() {
		Vector3f position = getGameObject().getLocalTransform().position;
		position.x += _velocityX;
		position.y += _velocityY;
		position.z += _velocityZ;
		
		_velocityX = 0;
		_velocityY = 0;
		_velocityZ = 0;
	}
	
	
}
