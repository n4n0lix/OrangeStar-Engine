package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.input.component.InputComponent;
import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.physic.component.UnitPhysicComponent;
import de.orangestar.engine.render.component.UnitRenderComponent;

public class WorldMap extends GameObject {

    public WorldMap() {       
        // Setup and link components
        UnitLogicComponent  logic   = new WorldMapLogicComponent();
        UnitPhysicComponent physic  = new UnitPhysicComponent(logic);
        InputComponent      input   = new WorldMapInputComponent(physic);
        UnitRenderComponent render  = new WorldMapRenderComponent(logic);

        // Set the components
        setLogicComponent(logic);
        setPhysicComponent(physic);
        setInputComponent(input);
        setRenderComponent(render);
    }

}
