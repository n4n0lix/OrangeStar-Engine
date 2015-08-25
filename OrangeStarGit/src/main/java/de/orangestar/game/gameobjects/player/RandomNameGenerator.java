package de.orangestar.game.gameobjects.player;

import java.util.Random;

public class RandomNameGenerator {

    public static String randomName() {
        return NAMES[RND.nextInt(NAMES.length)];
    }
    
    private static Random RND = new Random();
    
    private static String[] NAMES = new String[] {
            "Dave", "John", "Mark", "Eric", "Pad", "Bud", "Guz", "Tommy"
    };
    
}
