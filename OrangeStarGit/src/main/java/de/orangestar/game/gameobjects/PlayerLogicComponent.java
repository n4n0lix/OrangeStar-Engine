package de.orangestar.game.gameobjects;

import de.orangestar.engine.logic.component.UnitLogicComponent;

public class PlayerLogicComponent extends UnitLogicComponent {

    public PlayerLogicComponent() {
        // "Zoom" by 2x
        getTransform().scale(2f);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
    }

}
