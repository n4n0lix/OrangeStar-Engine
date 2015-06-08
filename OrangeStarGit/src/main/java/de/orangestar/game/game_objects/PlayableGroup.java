package de.orangestar.game.game_objects;

import java.util.ArrayList;
import java.util.List;

import de.orangestar.engine.logic.GameObject;

public class PlayableGroup extends GameObject {
	
	private List<Player> _playerList;
	
	public PlayableGroup(Player player) {
		_playerList = new ArrayList<>();
		_playerList.add(player);
	}
	
	public void addPlayer(Player player) {
		_playerList.add(player);
	}
	
	public void removePlayer(Player player) {
		_playerList.remove(player);
	}
}
