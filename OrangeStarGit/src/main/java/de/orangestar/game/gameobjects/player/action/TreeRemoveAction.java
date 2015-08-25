package de.orangestar.game.gameobjects.player.action;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;
import de.orangestar.game.gameobjects.tree.TreeModelLogicComponent;

/**
 * Action for spawning removing a Tree
 * 
 * @author Oliver &amp; Basti
 */
public class TreeRemoveAction extends Action {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public TreeRemoveAction(long treeUID) {
        _treeUID = treeUID;
    }

    @Override
    public void onStart() { }

    @Override
    public void onUpdate() {
        finish(); // after on tick the action is finished, there is no failure or success
        
        // 1# Get TreeModel
        GameObject obj = GameObject.getFirstByTagsAny("treemodel");
        
        if (obj == null) {
            return;
        }
        
        // 2# Get TreeModelLogicComponent
        TreeModelLogicComponent logic = obj.getComponent(TreeModelLogicComponent.class);
        
        if (logic == null) {
            return;
        }
        
        // 3# Remove tree
        logic.removeFlyweightByUID(_treeUID);
    }

    @Override
    public void onEnd() { }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private long _treeUID;

}
