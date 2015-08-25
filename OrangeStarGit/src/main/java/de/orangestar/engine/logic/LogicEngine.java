package de.orangestar.engine.logic;

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
	    _allWorlds = World.getAllWorlds();
	}

    @Override
    public void onUpdate() {
            updateTransforms();        
            updateLogic();
    }

	@Override
	public void onShutdown() { }
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private List<World> _allWorlds;
	
	private void updateTransforms() {
        for(int worldI = 0; worldI < _allWorlds.size(); worldI++) {

            List<GameObject> gameobjects = _allWorlds.get(worldI).getGameObjects();

            // LastTransform = CurrentTransform
            for(int i = 0; i < gameobjects.size(); i++) {
                GameObject obj = gameobjects.get(i);
                obj.setGlobalLastTransform(obj.getGlobalTransform());
            }
        }
	}

    private void updateLogic() {
        for(int worldI = 0; worldI < _allWorlds.size(); worldI++) {
            
            List<GameObject> gameobjects = _allWorlds.get(worldI).getGameObjects();
            
            for(int i = 0; i < gameobjects.size(); i++) {
                LogicComponent logic = gameobjects.get(i).getLogicComponent();
                    
                if (logic != null) {
                    logic.onUpdate();
                }
            }
        }
    }

}
