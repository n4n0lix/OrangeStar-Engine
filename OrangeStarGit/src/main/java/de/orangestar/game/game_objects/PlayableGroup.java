package de.orangestar.game.game_objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import de.orangestar.engine.logic.GameObject;

public class PlayableGroup extends GameObject implements Iterable<Player> {
	
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
