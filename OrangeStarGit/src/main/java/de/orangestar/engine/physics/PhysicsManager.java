package de.orangestar.engine.physics;

import de.orangestar.engine.AbstractManager;
import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;

public class PhysicsManager extends AbstractManager {

    @Override
    public void start() {

    }

    @Override
    public void update() {
        // 1# Move objects in world
        for(GameObject obj : World.Get()) {
            if (obj.getPhysicsModule() != null) {
                obj.getPhysicsModule().onUpdate();
            }
        }
        
        // 2# Check for collisions and revert to last transform
        // TODO
    }

    @Override
    public void shutdown() {

    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Singleton                             */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Returns the active instance of {@link PhysicsManager}
     * @return A physic manager
     */
    public static PhysicsManager Get() {
        if (INSTANCE == null) {
            INSTANCE = new PhysicsManager();
        }

        return INSTANCE;
    }

    private static PhysicsManager INSTANCE = null;
}
