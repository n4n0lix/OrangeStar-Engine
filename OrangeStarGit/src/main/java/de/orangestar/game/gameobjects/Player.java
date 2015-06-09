package de.orangestar.game.gameobjects;

import de.orangestar.engine.GameObject;

public class Player extends GameObject {

    public Player() {   
        // Setup and link components
        Player_Logic  logic  = new Player_Logic();
        Player_Input  input  = new Player_Input(logic);
        Player_Render render = new Player_Render(logic);

        // Set the components
        setInputModule(input);
        setLogicModule(logic);
        setRenderModule(render);
    }

}
