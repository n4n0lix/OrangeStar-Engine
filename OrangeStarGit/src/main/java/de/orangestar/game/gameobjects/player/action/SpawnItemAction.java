package de.orangestar.game.gameobjects.player.action;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.game.gameobjects.item.ItemModelLogicComponent;
import de.orangestar.game.gameobjects.item.ItemType;

/**
 * Action for spawning an Item
 * 
 * @author Oliver &amp; Basti
 */
public class SpawnItemAction extends Action {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public SpawnItemAction(ItemType type, int quantity, float x, float y) {
        _x = x;
        _y = y;
        _itemType = type;
    }

    @Override
    public void onStart() { }

    @Override
    public void onUpdate() {
        finish(); // after one tick the action is finished, there is no failure or success

        // 1# Get ItemModel
        GameObject obj = GameObject.getFirstByTagsAny("itemmodel");

        if (obj == null) {
            return;
        }

        // 2# Get ItemModelLogicComponent
        ItemModelLogicComponent logic = obj.getComponent(ItemModelLogicComponent.class);

        if (logic == null) {
            return;
        }

        // 3# Spawn Item
        logic.getItemSpawner(_itemType).spawnItem(new Vector3f(_x, _y, 0.0f));
    }

    @Override
    public void onEnd() { }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private float _x;
    private float _y;
    private ItemType _itemType;
}
