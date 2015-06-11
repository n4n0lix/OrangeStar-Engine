package de.orangestar.engine.physics.component;

import de.orangestar.engine.Component;
import de.orangestar.engine.GameObject;
import de.orangestar.engine.physics.PhysicsManager;

public abstract class PhysicsComponent extends Component {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public PhysicsComponent(GameObject obj) {
		super(obj);
	}

	/**
     * Implement physic updating code here. (Called every tick by the {@link PhysicsManager})
     */
    public abstract void onUpdate();

}