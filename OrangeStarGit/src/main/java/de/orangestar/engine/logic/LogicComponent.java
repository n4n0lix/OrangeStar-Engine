package de.orangestar.engine.logic;

import de.orangestar.engine.Component;

/**
 * The base class for logic modules.
 * A gameobject can have a logicmodule. It's task is to update and calculate its logical state.
 * 
 * @author Oliver &amp; Basti
 */
public class LogicComponent extends Component {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Implement component updates here. (Called on a frequently time by the corresponding manager)
     */
    public void onUpdate() { }
    
}
