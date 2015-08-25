package de.orangestar.game.gameobjects.map.surfaces;

import de.orangestar.engine.render.actor.tilemap.Surface;

public class DeepWater implements Surface {

	public static final DeepWater Instance = new DeepWater();
	
	@Override
	public int[] getTileIds() {
		return new int[] { 24, 25, 26, 27, 28, 29, 30, 31 };
	}

	@Override
	public boolean isTraversable() {
		return false;
	}

	@Override
	public int getLayer() {
		return 3;
	}
	
	@Override
	public boolean isSolid() {
	    return false;
	}
	
	@Override
	public String toString() {
	    return "DeepWater";
	}
	   
	private DeepWater() { }

}
