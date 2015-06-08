package de.orangestar.game.game_objects;

import de.orangestar.engine.logic.modules.RenderModule;
import de.orangestar.engine.render.actor.Image;
import de.orangestar.engine.resources.ResourceManager;
import de.orangestar.engine.values.Transform;
import de.orangestar.engine.values.Vector3f;

public class Player extends Unit {
	
	private boolean _isBusy;

    public Player() {
        // Setup rendering
        Image actor = new Image(ResourceManager.Get().getTexture("textures/PlayerDummy_16x16.png"));
        this._moduleRender = new RenderModule(this, actor);
        this._moduleRender.setLayer(10);
        
        // Scale x2
        this.setTransform(this.getTransform().scale(2f));
    }
    
    // not sure if we should let other classes manage the busy state or if we should let the class handle this itself.
    public void setBusyState(boolean b) {
    	_isBusy = b;
    }
    
    public boolean getBusyState() {
    	return _isBusy;
    }
}
