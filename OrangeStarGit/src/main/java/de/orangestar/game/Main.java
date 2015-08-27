package de.orangestar.game;

import de.orangestar.engine.Engine;
import de.orangestar.engine.debug.Logger;

/**
 * The engines main entry point.
 * 
 * @author Oliver &amp; Basti
 */
public class Main {

    /**
     * Main-Method.
     * @param args Command line arguments
     */
    public static void main(String[] args) { 
        
        Logger.addPrintStream(System.out);
        
        new Engine().run(MainGameState.class, args);
    }

}
