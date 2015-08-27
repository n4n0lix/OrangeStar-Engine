package de.orangestar.engine.render.component;

import java.util.List;

import de.orangestar.engine.render.Camera;
import de.orangestar.engine.render.IRenderEngine;
import de.orangestar.engine.render.RenderComponent;
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

    @Override
    public void onRender(IRenderEngine engine, Camera camera) {
        List<Actor> actors = getActors();
        
        for(int i = 0; i < actors.size(); i++) {
            actors.get(i).render(engine, getGameObject().getGlobalTransform());
        }
    }

    @Override
    protected void onInitialize() { }

    @Override
    protected void onDeinitialize() { }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

}
