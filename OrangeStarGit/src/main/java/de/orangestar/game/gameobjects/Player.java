package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.physics.component.UnitPhysicsComponent;
import de.orangestar.engine.render.component.UnitRenderComponent;
import de.orangestar.engine.values.Vector3f;

public class Player extends GameObject {

    public Player() {      	
        getLocalTransform().scale(new Vector3f(2f,2f,1f));
        
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
