package de.orangestar.game.gameobjects.item;

import java.util.HashMap;
import java.util.Map;

import de.orangestar.engine.logic.LogicComponent;
import de.orangestar.engine.render.RenderComponent;
import de.orangestar.engine.values.Transform;
import de.orangestar.engine.values.Vector3f;

/**
 * LogicComponent for the ItemModel.
 * 
 * @author Oliver &amp; Basti
 */
public class ItemModelLogicComponent extends LogicComponent {

    @Override
    protected void onInitialize() { }

    @Override
    protected void onDeinitialize() { }
    
	/**
	 * Sets an ItemSpawner if no ItemSpawner exists of this Item and returns it, otherwise just returns the Spawner.
	 * 
	 * @param itemType ItemType to set the spawner to
	 * @return the ItemSpawner
	 */
	public ItemSpawner getItemSpawner(ItemType itemType) {
		ItemSpawner spawner;
		
    	if(!_spawnerMap.containsKey(itemType)) {
    		spawner = new ItemSpawner(itemType);
    		_spawnerMap.put(itemType, spawner);    		
    	} else {
    		spawner = _spawnerMap.get(itemType);
    	}
    	
    	return spawner;
	}
	
	/**
	 * Spawns an Item.
	 * 
	 * @param itemType the Item to spawn
	 * @param position where the Item should be in the World
	 * @param quantity how often the Item spawns (how big the stack should be)
	 */
    public void spawnItem(ItemType itemType, Transform position, int quantity) {
    	getItemSpawner(itemType).spawnItem(position, quantity);
    }

    private Map<ItemType, ItemSpawner> _spawnerMap = new HashMap<>();
    
    /**
     * The class for the spawner of different Items.
     * 
     * @author Oliver, Basti
     */
    public class ItemSpawner {

    	/**
    	 * Creates an ItemSpawner.
    	 * @param type the ItemType to set the spawner to
    	 */
    	public ItemSpawner(ItemType type) {
    		_itemType = type;
    	}
    	
    	/**
    	 * Calls the spawn method with a Vector position.
    	 * 
    	 * @param position position of the Item in the world
    	 */
    	public void spawnItem(Vector3f position) {
    		spawnItem(new Transform(position), 1);
    	}
    	
    	/**
    	 * Calls the spawn method with a Transform position.
    	 * 
    	 * @param transform position of the Item in the world
    	 */
    	public void spawnItem(Transform transform) {
    		spawnItem(transform, 1);
    	}
    	
    	/**
    	 * Spawns the Item.
    	 * 
    	 * @param transform the position in the world
    	 * @param quantity How big the spawning stack is
    	 */
    	public void spawnItem(Transform transform, int quantity) {
    		// 1# Create Item
    		Item item = new Item(_itemType);
    		item.setQuantity(quantity);
    		
    		// 2# Create Item Flyweight
        	ItemFlyweight flyweight = new ItemFlyweight(item);
        	Transform.set( flyweight.getLocalTransform(), transform);
    		getGameObject().getWorld().addGameObject(flyweight);
    		
        	// 3# Add ItemModel rendering instance
        	RenderComponent render = getGameObject().getRenderComponent();
        	
        	if (render != null && render instanceof ItemModelRenderComponent) {
        		ItemModelRenderComponent itemModelRender = (ItemModelRenderComponent) render;
        		
        		itemModelRender.addItemInstance(item, transform);
        	}

    	}
    	
    	private ItemType _itemType;
    }

}
