package de.orangestar.engine.logic.modules;

import java.util.Comparator;

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
public class RenderModule extends Module {

    /**
     * Used to compare the rendering priority of two actors.
     * 
     * @author Basti
     */
    public static class RenderingPriorityComparer implements Comparator<RenderModule> {
        @Override
        public int compare(RenderModule o1, RenderModule o2) {
            return Integer.compare(o1.getLayer(), o2.getLayer());
        }
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor
     * @param parent The owner of this instance
     */
    public RenderModule(GameObject parent) {
        this(parent, null);
    }
    
    /**
     * Public-Constructor
     * @param parent The owner of this instance
     */
    public RenderModule(GameObject parent, Actor actor) {
        super(parent);
        setRootActor(actor);
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
     * Sets on which layer the root actor is rendered. Use this to control which actor
     * is displayed in front/back of other actors.
     * @param layer The layer
     */
    public void setLayer(int layer) {
        _layer = layer;
    }
    
    /**
     * Returns the layer on which the root actor is rendered.
     * @return A layer id
     */
    public int getLayer() {
        return _layer;
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
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Actor _actor;
    private int   _layer;
    
}
