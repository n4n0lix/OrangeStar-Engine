package de.orangestar.game.gameobjects.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.list.NodeCachingLinkedList;

import de.orangestar.game.gameobjects.player.action.Action;
import de.orangestar.game.gameobjects.player.action.ai.AIActionProcessor;

/**
 * A collection of players with an action distributing mechanism.
 * 
 * @author Oliver &amp; Basti
 */
public class PlayableGroup {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/**
	 * Public-Constructor
	 */
	public PlayableGroup() {
		_players = new ArrayList<>();
		_actions = new NodeCachingLinkedList<>();
	}
	
	/**
	 * Adds a player to the group.
	 * @param player A player
	 */
	public void addPlayer(Player player) {
		_players.add(player);
	}
	
	/**
	 * Removes a player from the group.
	 * @param player A player
	 */
	public void removePlayer(Player player) {
		_players.remove(player);
	}
	
	/**
	 * Returns a readonly list of all players of the group.
	 * @return a readonly list of all players of the group
	 */
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(_players);
	}
	
	/**
	 * Adds actions to the group that are later distributed actions.
	 * @param actions Multiple actions
	 */
	public void addActions(Action... actions) {
	    _actions.addAll(Arrays.asList(actions));
	}

	/**
	 * Distributes actions to idle players.
	 */
	public void doActions() {
	    assignActionsToIdlePlayers();
	}
	    
	/**
	 * Returns the nearest idle player to the tile location x/y.
	 * @param x The tile x
	 * @param y The tile y
	 * @return An idle player or null
	 */
    public Player getNearestIdlePlayer(int x, int y) {
        double distance = -1;
        Player result = null;
        
        for(Player player : _players) {
            AIActionProcessor processor = player.getLogicComponent().getAIActionProcessor();
            
            if (processor.isIdle()) {
                int tmpX = player.getLogicComponent().getTileLocation().x;
                int tmpY = player.getLogicComponent().getTileLocation().y;
                double tmpDistance = Math.sqrt(Math.pow(Math.abs(x - tmpX), 2) + Math.pow(Math.abs(y - tmpY), 2));

                if (distance == -1 || tmpDistance < distance) {
                    distance = tmpDistance;
                    result = player;
                }
            }
        }
        
        return result;
    }
	
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Assigns Actions to idle players.
     */
    private void assignActionsToIdlePlayers() {
        for(Player player : _players) {
            AIActionProcessor processor = player.getLogicComponent().getAIActionProcessor();
            if (processor.isIdle() && !_actions.isEmpty()) {
                processor.addAction(_actions.removeFirst());
            }
        }
    }
	
	private NodeCachingLinkedList<Action>    _actions;
    private List<Player>                     _players;
    
}
