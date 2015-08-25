package de.orangestar.game.gameobjects.tree;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.values.Anchor;

/**
 * Flyweight gameobject of a tree.
 * 
 * @author Oliver &amp; Basti
 */
public class TreeFlyweight extends GameObject {
	
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public static float  WIDTH  = 40;
    public static float  HEIGHT = 60;
    public static Anchor ANCHOR = Anchor.BOTTOM;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor.
     */
	public TreeFlyweight() {
	    setName("Tree#"+getUID());

        // Set the components
        setPhysicsComponent( new TreeFlyweightPhysicsComponent() );
	}
	
    public TreeFlyweightPhysicsComponent getPhysicsComponent() {
        return (TreeFlyweightPhysicsComponent) super.getPhysicsComponent();
    }

}
