package de.orangestar.game.gameobjects.item;

import de.orangestar.engine.render.Texture;

/**
 * Abstract class for the different ItemTypes in the game.
 * 
 * @author Oliver &amp; Basti
 */
public abstract class ItemType {
	
	/**
	 * 
	 * @return the name for the Item as String
	 */
	public abstract String  getName();
	
	/**
	 * 
	 * @return the texture of the Item
	 */
	public abstract String  getTexturePath();
	
	/**
	 * 
	 * @return the width of the Item
	 */
	public abstract float	getWidth();
	
	/**
	 * 
	 * @return the height of the Item
	 */
	public abstract float	getHeight();
	
	/**
	 * 
	 * @return the size of the stack, which the Item can have at most.
	 */
	public abstract int     getMaxStackSize();

}
