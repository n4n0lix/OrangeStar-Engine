package de.orangestar.engine.logic.modules;

import de.orangestar.engine.logic.GameObject;

public abstract class LogicModule extends Module {

    public LogicModule(GameObject parent) {
        super(parent);
    }

    public abstract void onUpdate();
}
