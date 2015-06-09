package de.orangestar.engine.render.component;

import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.render.actor.Actor;
import de.orangestar.engine.values.Transform;

public class UnitRenderComponent extends RenderComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    public UnitRenderComponent(UnitLogicComponent logic) {
        _logic = logic;
    }
    
    public void setActor(Actor actor) {
        _actor = actor;
    }
    
    @Override
    public void onRender() {
        if (_actor == null)  {
            return;
        }
        
        if (_logic == null) {
            return;
        }
        
        // Get the current and last transform
        Transform currentT = _logic.getTransform();
        Transform lastT    = _logic.getLastTransform();
        
        if (currentT == null || lastT == null) {
            return;
        }
        
        // We try to predict where the next transform of this rendermodule would be, to ensure smooth rendering
        Transform predictedT = Transform.interpolate(lastT, currentT, RenderManager.Get().getExtrapolation());

        _actor.render(predictedT);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                             Protected                              */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    protected UnitLogicComponent getUnitLogicComponent() {
        return _logic;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Actor _actor;
    private UnitLogicComponent _logic;

}
