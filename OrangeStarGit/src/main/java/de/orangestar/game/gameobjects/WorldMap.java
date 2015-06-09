package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;

public class WorldMap extends GameObject {

    public WorldMap() {       
        // Setup and link components
        WorldMap_Logic logic   = new WorldMap_Logic();
        WorldMap_Input input   = new WorldMap_Input(logic);
        WorldMap_Render render = new WorldMap_Render(logic);

        // Set the components
        setLogicModule(logic);
        setInputModule(input);
        setRenderModule(render);
    }

}
