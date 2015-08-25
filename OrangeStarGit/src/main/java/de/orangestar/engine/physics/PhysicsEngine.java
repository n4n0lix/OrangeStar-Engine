package de.orangestar.engine.physics;

import java.util.LinkedList;
import java.util.List;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;
import de.orangestar.engine.physics.collision.CollisionModel;
import de.orangestar.engine.values.Ray;
import de.orangestar.engine.values.Vector3f;

public class PhysicsEngine implements IPhysicsEngine {

    @Override
    public void onStart() {
        // No initialization needed
    }

    @Override
    public void onUpdate() {
        // 1# Move objects in world
        List<World> worlds = World.getAllWorlds();
        for(int worldI = 0; worldI < worlds.size(); worldI++) {
            
            List<GameObject> gameobjects = worlds.get(worldI).getGameObjects();
            
            for(int i = 0; i < gameobjects.size(); i++) {
                PhysicsComponent physics = gameobjects.get(i).getPhysicsComponent();
                
                if (physics != null) {
                    physics.onUpdate();
                }
            }
        }

        // 2# Check for collisions and revert to last transform
        // TODO
    }
    
    /**
     * Returns all gameobjects that intersects with the given ray.
     * @param ray The ray
     * @return A list of gameobjects
     */
    public List<GameObject> getGameObjectsByRayCast(final World world, final Ray ray) {
        List<GameObject>   gameobjects = world.getGameObjects();
        List<GameObject>   result = new LinkedList<>();
        
        // Test all gameobjects ... 
        for(int i = 0; i < gameobjects.size(); i++) {
            GameObject       gameObject = gameobjects.get(i);
            PhysicsComponent physics    = gameObject.getPhysicsComponent();
            
            // ... that have a PhysicsComponent ...
            if (physics != null) {
                CollisionModel   collision = physics.getCollisionModel();

                // ... and a collision model
                if (collision != null) {
                    // Translate the ray into Model-Space
                    Ray copyRay = new Ray();
                    Ray.set(copyRay, ray);
                    Ray.sub(copyRay, gameObject.getGlobalTransform().position);

                    // ... if the ray intersects the collision model
                    if (collision.intersects(copyRay)) {
                        result.add(gameObject);
                    }

                }
            }
        }
        
        return result;
    }
    
    /**
     * Returns all gameobjects for which the point is inside of their collision model.
     * @param vector The point to test for
     * @return A list of gameobjects
     */
    public List<GameObject> getGameObjectsByPoint(final World world, Vector3f vector) {
        List<GameObject>   gameobjects = world.getGameObjects();
        List<GameObject>   result = new LinkedList<>();
        
        // Test all gameobjects ... 
        for(int i = 0; i < gameobjects.size(); i++) {
            GameObject       gameObject = gameobjects.get(i);
            PhysicsComponent physics    = gameObject.getPhysicsComponent();
            
            // ... that have a PhysicsComponent ...
            if (physics != null) {
                CollisionModel   collision = physics.getCollisionModel();

                // ... and a collision model
                if (collision != null) {
                    Vector3f point = Vector3f.POOL.get();
                    
                    // Translate the point into Model-Space
                    Vector3f.set(point, vector);
                    Vector3f.sub(point, gameObject.getGlobalTransform().position);
                    
                    // ... if the point is inside the collision model
                    if (collision.intersects(point)) {
                        result.add(gameObject);
                    }
                    
                    Vector3f.POOL.release(point);
                }
            }
        }
        
        return result;
    }

    @Override
    public void onShutdown() {

    }

}
