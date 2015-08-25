package de.orangestar.game.gameobjects.item;

import de.orangestar.engine.logic.LogicComponent;
/**
 * The LogicComponent of the Flyweights.
 * 
 * @author Oliver &amp; Basti
 */
public class ItemFlyweightLogicComponent extends LogicComponent {

	/**
	 * Creates the LogicComponent.
	 * 
	 * @param item the Item, which the LogicComponent belongs to
	 */
	public ItemFlyweightLogicComponent(Item item) {
		_item = item;
	}
	
	/**
	 * 
	 * @return the Item
	 */
	public Item getItem() {
		return _item;
	}
	
	private Item _item;
	
}
