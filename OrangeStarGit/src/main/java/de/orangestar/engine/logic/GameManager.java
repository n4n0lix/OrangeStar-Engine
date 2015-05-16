package de.orangestar.engine.logic;

import java.io.File;
import java.util.Random;

import de.orangestar.engine.AbstractManager;
import de.orangestar.engine.debug.DebugManager;
import de.orangestar.engine.logic.modules.LogicModule;
import de.orangestar.engine.logic.modules.RenderModule;
import de.orangestar.engine.render.Material;
import de.orangestar.engine.render.actor.ATileMap;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.resources.ResourceManager;
import de.orangestar.engine.values.Quaternion4f;
import de.orangestar.engine.values.Transform;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.sandbox.TestGameObject;

/**
 * Handles the main logic of the game.
 * The game world and gamestates are managed here.
 * 
 * @author Basti
 */
public class GameManager extends AbstractManager {

    public static final long  TICK_RATE  = 100;
    public static final long  TICK_TIME  = 1000 / TICK_RATE;
    public static final float DELTA_TIME = (float) (1d / TICK_RATE);
      
	@Override
	public void start() {
        World.Get().addGameObject(new TestGameObject());
	}

    @Override
    public void update() {
        for(GameObject obj : World.Get()) {
            obj.setLastTransform(Transform.duplicate(obj.getTransform()));
        }

        LogicModule mod;
        for(GameObject obj : World.Get()) {
            if (obj._moduleLogic != null) {
                obj._moduleLogic.onUpdate();
            }
        }

    }

    @Override
    public boolean requestsExit() {
        return false;
    }
	
	@Override
	public void shutdown() {
		// TODO Add deinitialization code
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                              SINGLETON                             */
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
