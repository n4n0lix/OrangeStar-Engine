package de.orangestar.game.gameobjects.map.surfaces;

import de.orangestar.engine.render.actor.tilemap.Surface;

public class Water implements Surface {

	public static final Water Instance = new Water();
	
	@Override
	public int[] getTileIds() {
		return new int[] {  16, 17, 18, 19, 20, 21, 22, 23 };
	}

	@Override
	public boolean isTraversable() {
		return false;
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
	@Override
	public boolean isSolid() {
	    return false;
	}
	
    @Override
    public String toString() {
        return "Water";
    }
	
	private Water() { }

}
