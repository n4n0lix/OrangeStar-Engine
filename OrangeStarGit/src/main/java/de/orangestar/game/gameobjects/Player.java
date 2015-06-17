package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.physics.component.UnitPhysicsComponent;
import de.orangestar.engine.render.component.UnitRenderComponent;
import de.orangestar.engine.values.Vector3f;

public class Player extends GameObject {

    public Player() {      	        
        // Setup and link components
        UnitPhysicsComponent physic = new UnitPhysicsComponent(this);
        UnitLogicComponent   logic  = new PlayerLogicComponent(this, physic);
        UnitRenderComponent  render = new PlayerRenderComponent(this);
        
        // Set the components
        setLogicComponent(logic);
        setPhysicComponent(physic);
        setRenderComponent(render);
    }

}
