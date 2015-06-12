package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.physics.component.UnitPhysicsComponent;
import de.orangestar.engine.render.component.UnitRenderComponent;

public class WorldMap extends GameObject {

    public WorldMap() {     
        getLocalTransform().scale(4f);
        
        // Setup and link components
        UnitLogicComponent   logic   = new WorldMapLogicComponent(this);
        UnitPhysicsComponent physics = new UnitPhysicsComponent(this);
        InputComponent       input   = new WorldMapInputComponent(this, physics);
        UnitRenderComponent  render  = new WorldMapRenderComponent(this);

        // Set the components
        setLogicComponent(logic);
        setPhysicComponent(physics);
        setInputComponent(input);
        setRenderComponent(render);
    }

}
