package de.orangestar.engine.render.component;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.render.actor.Actor;
import de.orangestar.engine.values.Transform;

public class UnitRenderComponent extends RenderComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public UnitRenderComponent(GameObject obj) {
    	super(obj);
    }
    
    public void setActor(Actor actor) {
        _actor = actor;
    }

    @Override
    public void onRender() {
        if (_actor == null)  {
            return;
        }
        
        // Get the current and last transform
        Transform currentT = getGameObject().getGlobalTransform();
        Transform lastT    = getGameObject().getGlobalLastTransform();
        
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

}
