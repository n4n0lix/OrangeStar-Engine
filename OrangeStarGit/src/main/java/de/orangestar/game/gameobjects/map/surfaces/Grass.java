package de.orangestar.game.gameobjects.map.surfaces;

import de.orangestar.engine.render.actor.tilemap.Surface;

public class Grass implements Surface {

	public static final Grass Instance = new Grass();
	
	@Override
	public int[] getTileIds() {
		return new int[] { 8, 9, 10, 11, 12, 13, 14, 15 };
	}
	
	@Override
	public boolean isTraversable() {
		return true;
	}

	@Override
	public int getLayer() {
		return 0;
	}
	
	@Override
    public boolean isSolid() {
	    return false;
	}
	
	@Override
	public String toString() {
	    return "Grass";
	}
	   
	private Grass() { }

}
