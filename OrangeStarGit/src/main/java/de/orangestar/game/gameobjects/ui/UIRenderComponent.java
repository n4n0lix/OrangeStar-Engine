package de.orangestar.game.gameobjects.ui;

import de.orangestar.engine.render.actor.ui.Font;
import de.orangestar.engine.render.actor.ui.UILabel;
import de.orangestar.engine.render.component.SimpleRenderComponent;
import de.orangestar.engine.values.Anchor;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.game.MainGameState;

public class UIRenderComponent extends SimpleRenderComponent {

    @Override
    public void onInitialize() {
        super.onInitialize();
        
        Font basicFont = new BasicFont();
        
        UILabel text = new UILabel(basicFont, "ORANGESTAR INDEV by Olli und Basti");
        text.setAnchor(Anchor.TOP_LEFT);
        Vector3f.set(text.getTransform().scale, 2f, 2f, 1f);
        Vector3f.set(text.getTransform().position, 10f, 10f, 1f);
        addActor(text);
        
        UILabel text2 = new UILabel(basicFont, "Kill the animals, save the frames!");
        text2.setAnchor(Anchor.TOP_LEFT);
        Vector3f.set(text2.getTransform().scale, 3f, 3f, 1f);
        Vector3f.set(text2.getTransform().position, 10f, 30f, 1f);
        addActor(text2);
        
        UILabel text3 = new UILabel(basicFont, "1234567890!\"'§$%&/=?\\/()[]{}äöüÄÖÜß");
        text3.setAnchor(Anchor.TOP_LEFT);
        Vector3f.set(text3.getTransform().scale, 2f, 2f, 1f);
        Vector3f.set(text3.getTransform().position, 10f, 52f, 1f);
        addActor(text3);
    }
    
}
