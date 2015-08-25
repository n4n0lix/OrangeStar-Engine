package de.orangestar.game.gameobjects.tree;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.orangestar.engine.logic.LogicComponent;

/**
 * The {@link de.orangestar.engine.logic.LogicComponent} implementation of the {@link TreeModel}.
 * 
 * @author Oliver &amp; Basti
 */
public class TreeModelLogicComponent extends LogicComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    @Override
    public void onInitialize() {
        super.onInitialize();
        
        _trees = new LinkedList<TreeFlyweight>();
        _readOnlyTrees = Collections.unmodifiableList(_trees);
    }
    
    /**
     * Adds a flyweight to the model.
     * @param tree The flyweight
     */
    public void addFlyweight(TreeFlyweight tree) {
        // 1# Add flyweight gameobject
        _trees.add(tree);
        getGameObject().addChild(tree);
        
        // 2.1# Get renderer
        TreeModelRenderComponent modelRenderer = getGameObject().getComponent(TreeModelRenderComponent.class);
        
        if (modelRenderer == null) {
            return;
        }

        // 2.2# Add renderer instance
        modelRenderer.addFlyweight(tree);
    }
    
    /**
     * Adds multiple flyweights to the model.
     * @param flyweights The flyweights
     */
    public void addFlyweights(Collection<TreeFlyweight> flyweights) {
        for(TreeFlyweight tree : flyweights ) {
            addFlyweight(tree);
        }
    }
    
    /**
     * Removes a flyweight by a given uid from the model.
     * @param uid A game object uid
     */
    public void removeFlyweightByUID(long uid) {
        // 1# Find tree with uid
        TreeFlyweight tree = getFlyweightByUID(uid);
        
        if (tree == null) {
            return;
        }
        
        // 2# Remove tree
        _trees.remove(tree);
        tree.destroy();

        // 3.1# Get renderer
        TreeModelRenderComponent modelRenderer = getGameObject().getComponent(TreeModelRenderComponent.class);
            
        if (modelRenderer == null) {
            return;
        }

        // 3.2# Remove renderer instance
        modelRenderer.removeFlyweight(tree);
    }
    
    /**
     * Returns a flyweight by its UID or null if none was found by given UID.
     * @param uid A game object uid
     * @return A flyweight or null
     */
    public TreeFlyweight getFlyweightByUID(long uid) {
        for(int i = 0; i < _trees.size(); i++) {
            TreeFlyweight tree = _trees.get(i);
            if (tree.getUID() == uid) {
                return tree;
            }
        }
        
        return null;
    }
    
    /**
     * Returns a readonly list of all tree instances.
     * @return A readonly list of all tree instances
     */
    public List<TreeFlyweight> getTrees() {
        return _readOnlyTrees;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private List<TreeFlyweight> _trees;
    private List<TreeFlyweight> _readOnlyTrees;

}
