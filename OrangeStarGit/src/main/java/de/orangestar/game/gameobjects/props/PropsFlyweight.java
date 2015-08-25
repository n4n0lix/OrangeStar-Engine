package de.orangestar.game.gameobjects.props;

import de.orangestar.engine.GameObject;

/**
 * Flyweight for a Prop, to not have components classes for every Item.
 * 
 * @author Oliver &amp; Basti
 */
public class PropsFlyweight extends GameObject {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
	public PropsFlyweight(PropsType propstype) {
		setName("Props#" + getUID());

        // Set the components
        setLogicComponent( new PropsFlyweightLogicComponent( propstype ));
	}

}
