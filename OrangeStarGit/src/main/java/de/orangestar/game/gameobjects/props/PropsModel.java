package de.orangestar.game.gameobjects.props;

import de.orangestar.engine.GameObject;

/**
 * Model class for the props.
 * 
 * @author Oliver &amp; Basti
 */
public class PropsModel extends GameObject {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
	public PropsModel(){
		setName("PropsModel#" + getUID());
        addTags("propsmodel");

        // Set the components
        setLogicComponent( new PropsModelLogicComponent() );     
        setRenderComponent( new PropsModelRenderComponent() );
	}
	
    public PropsModelLogicComponent getLogicComponent() {
        return (PropsModelLogicComponent) super.getLogicComponent();
    }
    
    public PropsModelRenderComponent getRenderComponent() {
        return (PropsModelRenderComponent) super.getRenderComponent();
    }
	
}
