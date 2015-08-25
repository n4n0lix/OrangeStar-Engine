package de.orangestar.game.gameobjects.map.surfaces;

import de.orangestar.engine.render.actor.tilemap.Surface;

public class Sand implements Surface {

	public static final Sand Instance = new Sand();
	
	@Override
	public int[] getTileIds() {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, };
	}

	@Override
	public boolean isTraversable() {
		return true;
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
    @Override
    public boolean isSolid() {
        return false;
    }
    
    @Override
    public String toString() {
        return "Sand";
    }
    
	private Sand() { }
	
}
