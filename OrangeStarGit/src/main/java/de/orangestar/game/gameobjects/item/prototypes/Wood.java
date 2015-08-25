package de.orangestar.game.gameobjects.item.prototypes;

import de.orangestar.game.gameobjects.item.ItemType;

/**
 * Represents the ItemType Wood.
 * 
 * @author Oliver &amp; Basti
 */
public class Wood extends ItemType {
	
	public static final Wood Instance = new Wood();
	
	@Override
	public String getName() {
		return "Wood";
	}

	@Override
	public String getTexturePath() {
		return "textures/items/log1.png";
	}

	@Override
	public int getMaxStackSize() {
		return 10;
	}

	@Override
	public float getWidth() {
		return 14;
	}

	@Override
	public float getHeight() {
		return 14;
	}

	private Wood() { }
	
}
