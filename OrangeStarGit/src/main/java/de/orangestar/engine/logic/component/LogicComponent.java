package de.orangestar.engine.logic.component;

import de.orangestar.engine.Component;
import de.orangestar.engine.logic.GameManager;
import de.orangestar.engine.values.Transform;


/**
 * The base class for logic modules.
 * A gameobject can have a logicmodule. It's task is to update and calculate its logical state.
 * 
 * @author Basti
 */
public abstract class LogicComponent extends Component {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Implement logic updating code here. (Called every tick by the {@link GameManager})
     */
    public abstract void onUpdate();

}
