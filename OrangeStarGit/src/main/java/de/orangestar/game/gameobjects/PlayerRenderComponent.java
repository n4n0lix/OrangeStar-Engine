package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.render.GLWindow;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.actor.Image;
import de.orangestar.engine.render.component.UnitRenderComponent;
import de.orangestar.engine.values.Matrix4f;

public class PlayerRenderComponent extends UnitRenderComponent {

    public PlayerRenderComponent(GameObject obj) {
        super(obj);
        
        Image actor = new Image(new Texture("textures/PlayerDummy_16x16.png", true));
        
        setActor(actor);
        setLayer(10);
    }
    
    @Override
    public void onRender() {
        super.onRender();
    }

    
}
