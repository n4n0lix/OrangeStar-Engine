package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.physic.component.UnitPhysicComponent;
import de.orangestar.engine.render.component.UnitRenderComponent;

public class Player extends GameObject {

    public Player() {   
        // Setup and link components
        UnitLogicComponent  logic  = new PlayerLogicComponent();
        UnitPhysicComponent physic = new UnitPhysicComponent(logic);
        InputComponent      input  = new PlayerInputComponent(physic);
        UnitRenderComponent render = new PlayerRenderComponent(logic);
        
        // Set the components
        setLogicComponent(logic);
        setPhysicComponent(physic);
        setInputComponent(input);
        setRenderComponent(render);
    }

}
