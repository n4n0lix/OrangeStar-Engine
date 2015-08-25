package de.orangestar.game.gameobjects.ui;

import de.orangestar.engine.GameObject;

public class UI extends GameObject {

    public UI() {
        setName("UI#"+getUID());

        // Set the components
        setLogicComponent( new UILogicComponent() );
        setRenderComponent( new UIRenderComponent() );
    }
    
}
