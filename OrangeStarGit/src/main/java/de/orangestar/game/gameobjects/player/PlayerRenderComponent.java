package de.orangestar.game.gameobjects.player;

import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.actor.DebugMarker;
import de.orangestar.engine.render.actor.Image;
import de.orangestar.engine.render.actor.ui.Font;
import de.orangestar.engine.render.actor.ui.UILabel;
import de.orangestar.engine.render.component.UnitRenderComponent;
import de.orangestar.engine.values.Anchor;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.game.MainGameState;
import de.orangestar.game.gameobjects.ui.BasicFont;

/**
 * The {@link de.orangestar.engine.render.RenderComponent} of a player gameobject.
 * 
 * @author Oliver &amp; Basti
 */
public class PlayerRenderComponent extends UnitRenderComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
    public static Texture TEXTURE = new Texture("textures/players/player1.png", true);
	
    public static Font FONT = new BasicFont();
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    @Override
    public void onInitialize() {
        super.onInitialize();
        
        _actor = new Image.Builder()
                            .anchor(Player.ANCHOR)
                            .size(Player.WIDTH, Player.HEIGHT)
                            .texture(TEXTURE)
                            .build();

        addActor(_actor);
//        addActor(new DebugMarker(16f));

        // Name
        UILabel text = new UILabel(FONT, RandomNameGenerator.randomName());
        text.setAnchor(Anchor.TOP);
        Vector3f.set( text.getTransform().scale, 0.75f, 0.75f, 1f);
        addActor(text);
        setLayer(MainGameState.LAYER_PLAYER);
    }
    
    @Override
    public void onDeinitialize() {
        super.onDeinitialize();
        
        removeActor(_actor);
        _actor.onDeinitialize();
        _actor = null;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Image _actor;

}
