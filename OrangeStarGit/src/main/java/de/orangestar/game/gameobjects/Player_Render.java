package de.orangestar.game.gameobjects;

import de.orangestar.engine.render.actor.Image;
import de.orangestar.engine.render.component.UnitRenderComponent;
import de.orangestar.engine.resources.ResourceManager;

public class Player_Render extends UnitRenderComponent {

    public Player_Render(Player_Logic logic) {
        super(logic);
        
        Image actor = new Image(ResourceManager.Get().getTexture("textures/PlayerDummy_16x16.png"));
        
        setActor(actor);
        setLayer(10);
    }

    
}
