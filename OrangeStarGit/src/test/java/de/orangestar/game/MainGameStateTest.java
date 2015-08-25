package de.orangestar.game;

import org.junit.After;
import org.junit.Before;
import de.orangestar.engine.World;

public class MainGameStateTest {

    @Before
    public void before() {        
        gamestate = new MainGameState();
        gamestate.onStateStart();
        
        world = new World();
    }
    
    @After
    public void after() {
        gamestate.onStateEnd();
    }

    // Reenable when RenderEngine is properly seperated from logic
//    @Test
//    public void spawnPlayer() {
//        // #1 Player
//        Player player1 = gamestate.spawnNewPlayer(0, 0);
//        assertNotNull( player1 );
//        assertEquals( world.getGameObjects().size(), 1);
//        assertEquals( player1.getGlobalTransform().position.x, 0f, 0f);
//        assertEquals( player1.getGlobalTransform().position.y, 0f, 0f);
//        
//        // #2 Player
//        Player player2 = gamestate.spawnNewPlayer(-500, -500);
//        assertNotNull( player2 );
//        assertEquals( world.getGameObjects().size(), 2);
//        assertEquals( player2.getGlobalTransform().position.x, -500f, 0f);
//        assertEquals( player2.getGlobalTransform().position.y, -500f, 0f);
//        
//        // #3 Player
//        Player player3 = gamestate.spawnNewPlayer(3000, -3000);
//        assertNotNull( player3 );
//        assertEquals( world.getGameObjects().size(), 3);
//        assertEquals( player3.getGlobalTransform().position.x, 3000f, 0f);
//        assertEquals( player3.getGlobalTransform().position.y, -3000f, 0f);
//    }

    private World            world;
    private MainGameState    gamestate;
    
}
