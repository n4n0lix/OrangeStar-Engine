package de.orangestar.game.gameobjects.item;

/**
 * Base class for items.
 * 
 * @author Oliver &amp; Basti
 */
public class Item {

	/**
	 * Creates an item with a specified ItemType.
	 * @param type ItemType which defines the Item
	 */
	public Item(ItemType type) {
		_type = type;
	}
	
	/**
	 * 
	 * @return the ItemType
	 */
	public ItemType getItemType() {
		return _type;
	}
	
	/**
	 * Sets the quantity of the item.
	 * @param quantity how many items the stack should have
	 */
	public void setQuantity(int quantity) {
		if (_quantity < 0) {
			return;
		}
		
		_quantity = Math.min(_type.getMaxStackSize(), quantity);
	}
		
	private ItemType _type;
	private int      _quantity;
	
}
