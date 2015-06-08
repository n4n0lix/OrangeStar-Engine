package de.orangestar.game.game_objects;

import de.orangestar.engine.logic.modules.RenderModule;
import de.orangestar.engine.render.actor.Image;
import de.orangestar.engine.resources.ResourceManager;

public class Player extends Unit {
	
	private boolean _isBusy;

    public Player() {
        Image actor = new Image(ResourceManager.Get().getTexture("textures/PlayerDummy_16x16.png"));
        
        this._moduleRender = new RenderModule(this, actor);
    }
    
    // not sure if we should let other classes manage the busy state or if we should let the class handle this itself.
    public void setBusyState(boolean b) {
    	_isBusy = b;
    }
    
    public boolean getBusyState() {
    	return _isBusy;
    }
}
