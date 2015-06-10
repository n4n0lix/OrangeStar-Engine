package de.orangestar.engine.physic;

import de.orangestar.engine.AbstractManager;
import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;

public class PhysicManager extends AbstractManager {

    @Override
    public void start() {

    }

    @Override
    public void update() {
        // 1# Move objects in world
        for(GameObject obj : World.Get()) {
            if (obj.getPhysicModule() != null) {
                obj.getPhysicModule().onUpdate();
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
     * Returns the active instance of {@link PhysicManager}
     * @return A physic manager
     */
    public static PhysicManager Get() {
        if (INSTANCE == null) {
            INSTANCE = new PhysicManager();
        }

        return INSTANCE;
    }

    private static PhysicManager INSTANCE = null;
}
