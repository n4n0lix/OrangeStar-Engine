package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.physics.component.UnitPhysicsComponent;
import de.orangestar.engine.render.component.UnitRenderComponent;

public class Player extends GameObject {

    public Player() {      	
        // Setup and link components
        UnitLogicComponent   logic  = new PlayerLogicComponent(this);
        UnitPhysicsComponent physic = new UnitPhysicsComponent(this);
        UnitRenderComponent  render = new PlayerRenderComponent(this);
        
        // Set the components
        setLogicComponent(logic);
        setPhysicComponent(physic);
        setRenderComponent(render);
    }

}
