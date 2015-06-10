package de.orangestar.game.gameobjects;

import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.values.Vector3f;

public class WorldMapLogicComponent extends UnitLogicComponent {

    public WorldMapLogicComponent() {
        // "Zoom" by 2x
        getTransform().scale(2f);
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        
        Vector3f position = getTransform().position;
        
        // Create optical illusion of endless tilemap
        if (position.x > 0f) {
            position.x -= 32f;
        }
        
        if (position.x < -32f) {
            position.x += 32f;
        }
        
        if (position.y > 0f) {
            position.y -= 32f;
        }
        
        if (position.y < -32f) {
            position.y += 32f;
        }
    }
    
}
