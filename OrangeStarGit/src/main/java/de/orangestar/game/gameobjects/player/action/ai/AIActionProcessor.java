package de.orangestar.game.gameobjects.player.action.ai;

import de.orangestar.game.gameobjects.player.PlayerLogicComponent;
import de.orangestar.game.gameobjects.player.action.Action;
import de.orangestar.game.gameobjects.player.action.ActionProcessor;

/**
 * A {@link ActionProcessor} that is also capable of executing {@link AIAction}s.
 * @author 3funck
 *
 */
public class AIActionProcessor extends ActionProcessor {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public AIActionProcessor(PlayerLogicComponent aiUnit) {
        _aiUnit = aiUnit;
    }
    
    public void onPreStartAction(Action currentAction)      { 
        if (currentAction instanceof AIAction) {
            ((AIAction) currentAction).setAIUnit(_aiUnit);
        }
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private PlayerLogicComponent _aiUnit;
}
