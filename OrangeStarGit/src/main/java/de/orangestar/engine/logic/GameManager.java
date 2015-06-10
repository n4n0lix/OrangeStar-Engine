package de.orangestar.engine.logic;

import de.orangestar.engine.AbstractManager;
import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;
import de.orangestar.engine.input.InputManager;
import de.orangestar.engine.physic.PhysicManager;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.values.Transform;
import de.orangestar.game.MainGameStateDummy;

/**
 * Handles the main logic of the game.
 * The game world and gamestates are managed here.
 * 
 * @author Basti
 */
public class GameManager extends AbstractManager {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public static final long  TICK_RATE  = 100;
    public static final long  TICK_TIME  = 1000 / TICK_RATE;
    public static final float DELTA_TIME = (float) (1d / TICK_RATE);
          
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

	@Override
	public void start() {
	    gamestate = new MainGameStateDummy(); // TODO: Find a better way for gamestate injection
	    gamestate.onStateStart();
	}

    @Override
    public void update() {
        tickcurrent  = System.currentTimeMillis();
        tickduration = tickcurrent - tickprevious;
        tickprevious = tickcurrent;
        lag          += tickduration;

        
        // 1# Update the logic if we are behind
        while ( lag >= GameManager.TICK_TIME )
        {

            // 1.1# Manage Gamestate
            if (gamestate != null) {
                if (gamestate.hasFinished()) {
                    gamestate.onStateEnd();
                    
                    gamestate = gamestate.getNextGameState();
                    if (gamestate == null) {
                        break;
                    } else {
                        gamestate.onStateStart();
                    }
                }
                
                gamestate.onUpdate();
            }
        
            // 1.2# Handle new input
            InputManager.Get().update();
            
            // 1.3# Update the world
            for(GameObject obj : World.Get()) {
                if (obj.getLogicModule() != null) {
                    obj.getLogicModule().onUpdate();
                }
            }
            
            // 1.4# Calculate physics
            PhysicManager.Get().update();
            
            lag -= GameManager.TICK_TIME;
        }
        
        // 3# Tell the RenderManager how far in between of two ticks we are to extrapolate the rendering
        RenderManager.Get().setExtrapolation( 1f + (float) lag / (float) GameManager.TICK_TIME );
    }

    @Override
    public boolean requestsExit() {
        return gamestate == null;
    }
	
	@Override
	public void shutdown() {
		// TODO Add deinitialization code
	}

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
    private long tickcurrent  = System.currentTimeMillis();
    private long tickprevious = System.currentTimeMillis();
    private long tickduration = 0;
    private long lag = 0;
    
    private GameState gamestate;

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                              Singleton                             */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

	/**
	 * Returns the active instance of {@link GameManager}
	 * @return A game manager
	 */
	public static GameManager Get() {
		if (INSTANCE == null) {
			INSTANCE = new GameManager();
		}

		return INSTANCE;
	}

	private static GameManager INSTANCE = null;

}
