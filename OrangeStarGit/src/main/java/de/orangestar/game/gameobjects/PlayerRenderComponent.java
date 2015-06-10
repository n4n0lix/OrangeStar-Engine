package de.orangestar.game.gameobjects;

import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.actor.Image;
import de.orangestar.engine.render.component.UnitRenderComponent;

public class PlayerRenderComponent extends UnitRenderComponent {

    public PlayerRenderComponent(UnitLogicComponent logic) {
        super(logic);
        
        Image actor = new Image(new Texture("textures/PlayerDummy_16x16.png"));
        
        setActor(actor);
        setLayer(10);
    }

    
}
