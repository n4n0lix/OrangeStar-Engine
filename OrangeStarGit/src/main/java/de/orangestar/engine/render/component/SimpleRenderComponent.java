package de.orangestar.engine.render.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.orangestar.engine.render.Camera;
import de.orangestar.engine.render.IRenderEngine;
import de.orangestar.engine.render.RenderComponent;
import de.orangestar.engine.render.RenderEngine;
import de.orangestar.engine.render.actor.Actor;

/**
 * A simple implementation of a render component.
 * 
 * @author Oliver &amp; Basti
 */
public class SimpleRenderComponent extends RenderComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public void onInitialize() {
        _actors = new ArrayList<>();
        _readOnlyActors = Collections.unmodifiableList(_actors);
    }

    @Override
    public void onUpdate(IRenderEngine engine, Camera camera) {
        for(int i = 0; i < _actors.size(); i++) {
            _actors.get(i).render(engine, getGameObject().getGlobalTransform());
        }
    }

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

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private List<Actor> _actors;
    private List<Actor> _readOnlyActors;

}
