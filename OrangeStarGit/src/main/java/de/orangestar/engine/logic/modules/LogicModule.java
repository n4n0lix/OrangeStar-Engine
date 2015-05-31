package de.orangestar.engine.logic.modules;

import de.orangestar.engine.logic.GameObject;

/**
 * The base class for logic modules.
 * A gameobject can have a logicmodule. It's task is to update and calculate its logical state.
 * 
 * @author Basti
 */
public abstract class LogicModule extends Module {

    public LogicModule(GameObject parent) {
        super(parent);
    }

    /**
     * Implement logic updating code here. (Called every tick by the {@link GameManager})
     */
    public abstract void onUpdate();
}
