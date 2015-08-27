package de.orangestar.engine.physics;

import java.util.List;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;
import de.orangestar.engine.values.Ray;
import de.orangestar.engine.values.Vector3f;

public interface IPhysicsEngine {

    public void         onStart();
    
    public void         onUpdate();
    
    public void         onShutdown();
     
    public List<GameObject> getGameObjectsByPoint(final World world, Vector3f vector);
    
    public List<GameObject> getGameObjectsByRayCast(final World world, final Ray ray);
    
    public void         addWorld(World world);
    public void         removeWorld(World world);
    
}
