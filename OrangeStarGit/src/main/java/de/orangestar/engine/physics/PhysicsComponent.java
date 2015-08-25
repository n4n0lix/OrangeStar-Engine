package de.orangestar.engine.physics;

import de.orangestar.engine.Component;
import de.orangestar.engine.logic.LogicEngine;
import de.orangestar.engine.physics.collision.CollisionModel;
import de.orangestar.engine.values.Vector3f;

/**
 * The base implementation of a physics component.
 *
 * @author Oliver &amp; Basti
 */
public class PhysicsComponent extends Component {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Returns the collision model.
     * @return The collision model
     */
    public CollisionModel getCollisionModel() {
        return _collisionModel;
    }
    
    /**
     * Sets the collision model.
     * @param collisionModel The collision model
     */
    public void setCollisionModel(CollisionModel collisionModel) {
        _collisionModel = collisionModel;
    }

    /**
     * Teleports the gameobject to given world position.
     * @param x The x value
     * @param y The y value
     * @param z The z value
     */
    public void teleport(float x, float y, float z) {
        Vector3f position = getGameObject().getLocalTransform().position;
        position.x = x;
        position.y = y;
        position.z = z;
    }

    /**
     * Teleports the gameobject to given world position.
     * @param position The position
     */
    public void teleport(Vector3f position) {
        Vector3f.set(getGameObject().getLocalTransform().position, position);
    }

    /**
     * 
     */
    public void onUpdate() {       
        Vector3f position = getGameObject().getLocalTransform().position;
        
        position.x += _velocityX * LogicEngine.DELTA_TIME;
        position.y += _velocityY * LogicEngine.DELTA_TIME;
        position.z += _velocityZ * LogicEngine.DELTA_TIME;
        
        _velocityX = 0;
        _velocityY = 0;
        _velocityZ = 0;
    }

    /**
     * Adds velocity on the x axis.
     * @param velocity The added velocity
     */
    public void addVelocityX(float velocity) {
        _velocityX += velocity;
    }

    /**
     * Adds velocity on the y axis.
     * @param velocity The added velocity
     */
    public void addVelocityY(float velocity) {
        _velocityY += velocity;
    }

    /**
     * Adds velocity on the z axis.
     * @param velocity The added velocity
     */
    public void addVelocityZ(float velocity) {
        _velocityZ += velocity;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private CollisionModel _collisionModel;
    private float _velocityX;
    private float _velocityY;
    private float _velocityZ;

}