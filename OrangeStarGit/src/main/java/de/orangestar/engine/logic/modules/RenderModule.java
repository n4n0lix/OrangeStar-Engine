package de.orangestar.engine.logic.modules;

import de.orangestar.engine.logic.GameObject;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.render.actor.Actor;
import de.orangestar.engine.values.Transform;

/**
 * The base class for render modules.
 * A gameobject can have a rendermodule. It's task is to visually represent the gameobject.
 * 
 * @author Basti
 */
public abstract class RenderModule extends Module {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public RenderModule(GameObject parent) {
        super(parent);
    }

    /**
     * Set the root actor for this rendermodule.
     * @param actor An actor
     */
    public void setRootActor(Actor actor) {
        _actor = actor;
    }
    
    /**
     * Returns the root actor of this rendermodule.
     * @return An actor
     */
    public Actor getRootActor() {
        return _actor;
    }
    
    /**
     * Renders this rendermodule. (Called every frame from the {@link RenderManager})
     */
    public void render() {
        if (_actor == null)  {
            return;
        }
        
        // Get the parent gameobject
        GameObject obj = getGameObject();
        
        if (obj == null) {
            return;
        }
        
        // Get the current and last transform
        Transform currentT = obj.getTransform();
        Transform lastT = obj.getLastTransform();
        
        if (currentT == null || lastT == null) {
            return;
        }
        
        // We try to predict where the next transform of this rendermodule would be, to ensure smooth rendering
        Transform predictedT = Transform.interpolate(lastT, currentT, RenderManager.Get().getExtrapolation());

        _actor.render(predictedT);
    }
    
    /**
     * Implement rendering code here.
     */
    public abstract void onRender();
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Actor _actor;
}
