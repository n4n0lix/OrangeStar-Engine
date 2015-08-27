package de.orangestar.engine.input;

import de.orangestar.engine.Component;

/**
 * The base class of all input components that handle the input processing of gameobjects.
 * 
 * @author Oliver &amp; Basti
 */
public abstract class InputComponent extends Component {
	
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Implement component updates here. (Called on a frequently time by the corresponding manager)
     */
    public abstract void onUpdate();
    
}
