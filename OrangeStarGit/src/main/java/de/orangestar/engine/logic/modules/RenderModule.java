package de.orangestar.engine.logic.modules;

import de.orangestar.engine.logic.GameObject;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.render.actor.Actor;
import de.orangestar.engine.values.Transform;

public abstract class RenderModule extends Module {

    public RenderModule(GameObject parent) {
        super(parent);
    }

    public void setActor(Actor actor) {
        _actor = actor;
    }
    
    public Actor getActor() {
        return _actor;
    }
    
    public void render() {
        if (_actor == null)  {
            return;
        }
        
        GameObject obj = getGameObject();
        
        if (obj == null) {
            return;
        }
        
        Transform newT = obj.getTransform();
        Transform oldT = obj.getLastTransform();
        
        if (newT == null || oldT == null) {
            return;
        }
        
        Transform predictedT = Transform.interpolate(oldT, newT, RenderManager.Get().getExtrapolation());

        _actor.render(predictedT);
    }
    
    public abstract void onRender();
    
    private Actor _actor;
}
