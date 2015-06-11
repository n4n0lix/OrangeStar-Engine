package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.values.Vector3f;

public class WorldMapLogicComponent extends UnitLogicComponent {
    
    public WorldMapLogicComponent(GameObject obj) {
		super(obj);
	}

	@Override
    public void onUpdate() {
        super.onUpdate();
        
        Vector3f position = getGameObject().getLocalTransform().position;
        
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
