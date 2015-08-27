package de.orangestar.engine.physics;

import java.util.ArrayList;
import java.util.List;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;
import de.orangestar.engine.values.Ray;
import de.orangestar.engine.values.Vector3f;

public class DisabledPhysicsEngine implements IPhysicsEngine {

    @Override
    public void onStart() { }

    @Override
    public void onUpdate() { }

    @Override
    public void onShutdown() { }

    @Override
    public List<GameObject> getGameObjectsByPoint(World world, Vector3f vector) {
        return new ArrayList<GameObject>();
    }

    @Override
    public List<GameObject> getGameObjectsByRayCast(World world, Ray ray) {
        return new ArrayList<GameObject>();
    }

    @Override
    public void addWorld(World world) { }

    @Override
    public void removeWorld(World world) { }

}
