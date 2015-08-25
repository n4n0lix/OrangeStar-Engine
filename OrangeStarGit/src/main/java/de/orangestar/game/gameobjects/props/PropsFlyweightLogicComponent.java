package de.orangestar.game.gameobjects.props;

import de.orangestar.engine.logic.LogicComponent;

/**
 * Logic component of the flyweight prop.
 * 
 * @author Oliver &amp; Basti
 */
public class PropsFlyweightLogicComponent extends LogicComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
	public PropsFlyweightLogicComponent(PropsType propstype) {
		_propsType = propstype;
	}
	
	public PropsType getItem() {
		return _propsType;
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private PropsType _propsType;
	
}
