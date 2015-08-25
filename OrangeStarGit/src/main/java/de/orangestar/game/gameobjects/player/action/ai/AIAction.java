package de.orangestar.game.gameobjects.player.action.ai;

import de.orangestar.game.gameobjects.player.PlayerLogicComponent;
import de.orangestar.game.gameobjects.player.action.Action;

/**
 * An action that can only be execute by an ai driven unit.
 * 
 * @author 3funck
 */
public abstract class AIAction extends Action {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Public                                */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/**
	 * Sets the ai driven unit for this action.
	 * @param logic The ai unit
	 */
    public void setAIUnit(PlayerLogicComponent logic) {
        _aiUnit = logic;
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                             Protected                              */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Returns the ai driven unit.
     */
    protected PlayerLogicComponent getAIUnit() {
        return _aiUnit;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private PlayerLogicComponent _aiUnit;

}
