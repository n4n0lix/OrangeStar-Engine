package de.orangestar.engine.physic.component;

import de.orangestar.engine.Component;
import de.orangestar.engine.physic.PhysicManager;

public abstract class PhysicComponent extends Component {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Implement physic updating code here. (Called every tick by the {@link PhysicManager})
     */
    public abstract void onUpdate();

}