package de.orangestar.engine.logic;

import de.orangestar.engine.World;

public class DisabledLogicEngine implements ILogicEngine {

    @Override
    public void onStart() { }

    @Override
    public void onUpdate() { }

    @Override
    public void onShutdown() { }

    @Override
    public void addWorld(World world) { }

    @Override
    public void removeWorld(World world) { }

}
