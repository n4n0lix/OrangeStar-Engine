package de.orangestar.game.gameobjects.player;

import de.orangestar.engine.physics.PhysicsComponent;
import de.orangestar.engine.physics.collision.CollisionAABB;
import de.orangestar.engine.values.Vector3f;

/**
 * The {@link de.orangestar.engine.physics.PhysicsComponent} of a player gameobject.
 * 
 * @author Oliver &amp; Basti
 */
public class PlayerPhysicsComponent extends PhysicsComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    @Override
    public void onInitialize() {
        super.onInitialize();
        
        setupCollisionBox();
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private void setupCollisionBox() {
        Vector3f firstEdge  = Vector3f.POOL.get();
        Vector3f secondEdge = Vector3f.POOL.get();
        
        firstEdge.x  = -Player.WIDTH/2;
        firstEdge.y  = -Player.HEIGHT;
        firstEdge.z  = 0.0f;
        
        secondEdge.x = Player.WIDTH/2;
        secondEdge.y = 0;
        secondEdge.z = Player.WIDTH/2;
        
        setCollisionModel( new CollisionAABB(firstEdge, secondEdge));
        
        Vector3f.POOL.release(firstEdge);
        Vector3f.POOL.release(secondEdge);
    }

}
