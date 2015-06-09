package de.orangestar.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.orangestar.engine.GameObject;
import de.orangestar.game.gameobjects.Player;

public class PlayableGroup implements Iterable<Player> {
	
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
	
	public List<Player> getPlayers() {
		return Collections.unmodifiableList(_playerList);
	}

    @Override
    public Iterator<Player> iterator() {
        return _playerList.iterator();
    }
    
}
