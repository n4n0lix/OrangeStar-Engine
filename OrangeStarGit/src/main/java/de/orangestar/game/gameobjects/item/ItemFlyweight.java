package de.orangestar.game.gameobjects.item;

import de.orangestar.engine.GameObject;
/**
 * Flyweight for an Item, to not have components classes for every Item.
 * 
 * @author Oliver &amp; Basti
 */
public class ItemFlyweight extends GameObject {

	/**
	 * Creates an ItemFlyweight.
	 * 
	 * @param item the Item which the Flyweight should have
	 */
	public ItemFlyweight(Item item) {
		setName("Item#" + getUID());

        setLogicComponent( new ItemFlyweightLogicComponent(item) );
	}

}
