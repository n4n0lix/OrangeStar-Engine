package de.orangestar.game.gameobjects.player.action;

/**
 * An action that waits a specified duration and then ends.
 * 
 * @author Oliver &amp; Basti
 */
public class WaitAction extends Action {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Public                                */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
    /**
     * Public-Constructor
     * @param durationInTicks The duration in ticks. See {@linkplain de.orangestar.engine.logic.LogicEngine#TICK_RATE} for ticks per second.
     */
    public WaitAction(long durationInTicks) {
        _durationTicks = durationInTicks;
    }
    
    @Override
    public void onStart() {
        _tickCounter = 0;
    }

    @Override
    public void onUpdate() {
        _tickCounter++;
        
        if (_tickCounter >= _durationTicks) {
            finish();
        }
    }

    @Override
    public void onEnd() {
        // Do nothing
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private long _durationTicks;
    private long _tickCounter;
    
}
