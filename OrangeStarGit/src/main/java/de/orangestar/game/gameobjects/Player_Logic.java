package de.orangestar.game.gameobjects;

import de.orangestar.engine.logic.component.UnitLogicComponent;

public class Player_Logic extends UnitLogicComponent {

    public Player_Logic() {
        // "Zoom" by 2x
        getTransform().scale(2f);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
    }

}
