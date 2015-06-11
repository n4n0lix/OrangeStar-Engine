package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.physics.component.UnitPhysicsComponent;
import de.orangestar.engine.render.component.UnitRenderComponent;

public class Player extends GameObject {

    public Player() {   
    	getLocalTransform().scale(2f);
    	
        // Setup and link components
        UnitLogicComponent   logic  = new PlayerLogicComponent(this);
        UnitPhysicsComponent physic = new UnitPhysicsComponent(this);
        InputComponent       input  = new PlayerInputComponent(this, physic);
        UnitRenderComponent  render = new PlayerRenderComponent(this);
        
        // Set the components
        setLogicComponent(logic);
        setPhysicComponent(physic);
        setInputComponent(input);
        setRenderComponent(render);
    }

}
