package de.orangestar.engine.logic;

import java.util.ArrayList;
import java.util.List;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;

/**
 * Handles the main logic of the game.
 * The game world and gamestates are managed here.
 * 
 * @author Oliver &amp; Basti
 */
public class LogicEngine implements ILogicEngine {
          
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

	@Override
	public void onStart() { 
	    _worlds = new ArrayList<>();
	}

    @Override
    public void onUpdate() {
        updateTransforms();
        updateLogic();
    }

	@Override
	public void onShutdown() { }

    @Override
    public void addWorld(World world) {
        _worlds.add(world);
    }

    @Override
    public void removeWorld(World world) {
        _worlds.add(world);
    }
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private List<World> _worlds;
	
	private void updateTransforms() {
        for(int worldI = 0; worldI < _worlds.size(); worldI++) {

            List<GameObject> gameobjects = _worlds.get(worldI).getGameObjects();

            // LastTransform = CurrentTransform
            for(int i = 0; i < gameobjects.size(); i++) {
                GameObject obj = gameobjects.get(i);
                obj.setGlobalLastTransform(obj.getGlobalTransform());
            }
        }
	}
	
    private void updateLogic() {
        for(int worldI = 0; worldI < _worlds.size(); worldI++) {
            
            List<GameObject> gameobjects = _worlds.get(worldI).getGameObjects();
            
            for(int i = 0; i < gameobjects.size(); i++) {
                GameObject gameObject = gameobjects.get(i);
                LogicComponent logic = gameObject.getLogicComponent();
                    
                if (logic != null) {                    
                    logic.onUpdate();
                }
            }
        }
    }

}
