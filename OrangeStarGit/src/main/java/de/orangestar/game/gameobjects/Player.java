package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;

public class Player extends GameObject {

    public Player() {   
        // Setup and link components
        Player_Logic  logic  = new Player_Logic();
        Player_Render render = new Player_Render(logic);

        // Set the components
        setLogicModule(logic);
        setRenderModule(render);
    }

}
