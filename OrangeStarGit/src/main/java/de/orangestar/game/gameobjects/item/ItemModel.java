package de.orangestar.game.gameobjects.item;

import de.orangestar.engine.GameObject;
/**
 * ItemModel class to link the ItemComponents.
 * 
 * @author Oliver &amp; Basti
 */
public class ItemModel extends GameObject {

	/**
	 * Creates an ItemModel.
	 */
	public ItemModel(){
		setName("ItemModel#" + getUID());
        addTags("itemmodel");

        setLogicComponent( new ItemModelLogicComponent() );
        setRenderComponent( new ItemModelRenderComponent() );
	}
	
	@Override
    public ItemModelLogicComponent getLogicComponent() {
        return (ItemModelLogicComponent) super.getLogicComponent();
    }
    
	@Override
    public ItemModelRenderComponent getRenderComponent() {
        return (ItemModelRenderComponent) super.getRenderComponent();
    }
	
}
