package de.orangestar.engine.render.component;

import java.util.List;

import de.orangestar.engine.render.Camera;
import de.orangestar.engine.render.IRenderEngine;
import de.orangestar.engine.render.RenderComponent;
import de.orangestar.engine.render.actor.Actor;
import de.orangestar.engine.values.Transform;

/**
 * The unit implementation of the render component. The rendering position is
 * interpolated between the current and the last transform to enable smooth
 * rendering for moving gameobjects.
 * 
 * @author Oliver &amp; Basti
 */
public class UnitRenderComponent extends RenderComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    @Override
    public void onRender(IRenderEngine engine, Camera camera) {
        if (getActors().isEmpty())  {
            return;
        }
        
        // Get the current and last transform
        Transform currentT = getGameObject().getGlobalTransform();
        Transform lastT    = getGameObject().getGlobalLastTransform();
        
        if (currentT == null || lastT == null) {
            return;
        }
        
        // We try to predict where the next transform of this rendermodule would be, to ensure smooth rendering
        
        Transform predictedT = Transform.POOL.get();
        Transform.set(predictedT, lastT);
        Transform.interpolate(predictedT, currentT, engine.getExtrapolation());

        List<Actor> actors = getActors();
        
        for(int i = 0; i < actors.size(); i++) {
            actors.get(i).render(engine, predictedT);
        }
        
        Transform.POOL.release(predictedT);
    }

    @Override
    protected void onInitialize() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void onDeinitialize() {
        // TODO Auto-generated method stub
        
    }
}
