package de.orangestar.engine.render;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.orangestar.engine.Component;
import de.orangestar.engine.GameObject;
import de.orangestar.engine.render.actor.Actor;

/**
 * The base class for render modules.
 * A gameobject can have a rendermodule. It's task is to visually represent the gameobject.
 * 
 * @author Oliver &amp; Basti
 */
public abstract class RenderComponent extends Component {

	/**
     * Used to compare the rendering priority of two actors.
     * 
     * @author Basti
     */
    public static class PriorityComparer implements Comparator<RenderComponent> {
        @Override
        public int compare(RenderComponent o1, RenderComponent o2) {
            return Integer.compare(o1.getLayer(), o2.getLayer());
        }
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public RenderComponent() {
        _actors = new ArrayList<Actor>();
        _readOnlyActors = Collections.unmodifiableList(_actors);
    }
    
    @Override
    public void deinitialize() {
        super.deinitialize();
        _actors.clear();
    }
    
    /**
     * Implement component updates here. (Called on a frequently time by the corresponding manager)
     */
    public abstract void onRender(IRenderEngine engine, Camera camera);
        
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

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Add an actor.
     * @param actor A actor
     */
    public void addActor(Actor actor) {
        _actors.add(actor);
    }

    /**
     * Removes an actor.
     * @param actor A actor
     */
    public void removeActor(Actor actor) {
        _actors.remove(actor);
    }

    /**
     * Returns an unmodifiable list of all actors.
     * @return An unmodifiable list of all actors
     */
    public List<Actor> getActors() {
        return _readOnlyActors;
    }

    private int        _layer;
    private List<Actor> _actors;
    private List<Actor> _readOnlyActors;
    
}
