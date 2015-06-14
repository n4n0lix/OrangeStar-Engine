package de.orangestar.game.gameobjects.map;

import java.util.HashMap;
import java.util.Map;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.tools.random.NoiseGenerator;
import de.orangestar.engine.tools.random.PerlinNoiseGenerator;
import de.orangestar.engine.utils.Pair;

public class MapLogicComponent extends UnitLogicComponent {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public MapLogicComponent(GameObject obj) {
		super(obj);
	}

	@Override
    public void onUpdate() {
        super.onUpdate();
    }

	public void addChunk(int x, int y, MapChunk chunk) {
	    Pair<Integer, Integer> chunkId = Pair.New(chunk.getX(), chunk.getY());
	    _chunks.put(chunkId, chunk);
	}
	
    public MapChunk getChunk(int x, int y) {
        return _chunks.get(Pair.New(x,y));
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Map<Pair<Integer,Integer>, MapChunk> _chunks = new HashMap<>();

}
