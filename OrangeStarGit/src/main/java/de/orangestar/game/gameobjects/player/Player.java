package de.orangestar.game.gameobjects.player;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.physics.PhysicsComponent;
import de.orangestar.engine.render.RenderEngine;
import de.orangestar.engine.values.Anchor;
import de.orangestar.game.gameobjects.tree.TreeModelRenderComponent;

/**
 * The player gameobject.
 * 
 * @author Oliver &amp; Basti
 */
public class Player extends GameObject {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
    public static float  WIDTH  = 16;
    public static float  HEIGHT = 28;
    public static Anchor ANCHOR = Anchor.BOTTOM;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public Player() {      	      
        setName("Player#"+getUID());
        
        // Set the components
        setLogicComponent( new PlayerLogicComponent() );
        setPhysicsComponent( new PlayerPhysicsComponent() );
        setRenderComponent( new PlayerRenderComponent() );
    }

    public PlayerLogicComponent getLogicComponent() {
        return (PlayerLogicComponent) super.getLogicComponent();
    }
    
    public PlayerRenderComponent getRenderComponent() {
        return (PlayerRenderComponent) super.getRenderComponent();
    }
    
    public PhysicsComponent getPhysicsComponent() {
        return (PhysicsComponent) super.getPhysicsComponent();
    }
    
}
