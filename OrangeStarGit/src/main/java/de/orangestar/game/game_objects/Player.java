package de.orangestar.game.game_objects;

import de.orangestar.engine.logic.modules.RenderModule;
import de.orangestar.engine.render.actor.Image;
import de.orangestar.engine.resources.ResourceManager;

public class Player extends Unit {

    public Player() {
        Image actor = new Image(ResourceManager.Get().getTexture("textures/PlayerDummy_16x16.png"));
        
        this._moduleRender = new RenderModule(this, actor);
    }
}
