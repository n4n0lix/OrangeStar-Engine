package de.orangestar.game.gameobjects.tree;

import de.orangestar.engine.physics.PhysicsComponent;
import de.orangestar.engine.physics.collision.CollisionAABB;
import de.orangestar.engine.values.Vector3f;

/**
 * The {@link de.orangestar.engine.physics.PhysicsComponent} implementation of the {@link TreeFlyweight}.
 * 
 * @author Oliver &amp; Basti
 */
public class TreeFlyweightPhysicsComponent extends PhysicsComponent {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    @Override
    protected void onInitialize() {
        setupCollisionBox();
    }
    
    @Override
    protected void onDeinitialize() { }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Sets the collision box up.
     */
    private void setupCollisionBox() {
        Vector3f firstEdge  = Vector3f.POOL.get();
        Vector3f secondEdge = Vector3f.POOL.get();
        
        firstEdge.x  = -TreeFlyweight.WIDTH/2;
        firstEdge.y  = -TreeFlyweight.HEIGHT;
        firstEdge.z  = 0.0f;
        
        secondEdge.x = TreeFlyweight.WIDTH/2;
        secondEdge.y = 0;
        secondEdge.z = TreeFlyweight.WIDTH/2;
        
        setCollisionModel( new CollisionAABB(firstEdge, secondEdge));
        
        Vector3f.POOL.release(firstEdge);
        Vector3f.POOL.release(secondEdge);
    }
    
}
