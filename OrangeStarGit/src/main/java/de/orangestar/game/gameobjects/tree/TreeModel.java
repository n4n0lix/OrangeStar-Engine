package de.orangestar.game.gameobjects.tree;

import de.orangestar.engine.GameObject;

/**
 * Flyweight-Model of {@linkplain TreeFlyweight}.
 * 
 * @author Oliver &amp; Basti
 */
public class TreeModel extends GameObject {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public TreeModel() {  
        setName("TreeModel#"+getUID());
        
        // Initialize Test Data
        addTags("treemodel");

        // Set the components
        setLogicComponent( new TreeModelLogicComponent() );
        setRenderComponent( new TreeModelRenderComponent() );
    }

    public TreeModelLogicComponent getLogicComponent() {
        return (TreeModelLogicComponent) super.getLogicComponent();
    }
    
    public TreeModelRenderComponent getRenderComponent() {
        return (TreeModelRenderComponent) super.getRenderComponent();
    }

}
