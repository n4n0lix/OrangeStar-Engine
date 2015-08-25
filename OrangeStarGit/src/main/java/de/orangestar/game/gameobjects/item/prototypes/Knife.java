package de.orangestar.game.gameobjects.item.prototypes;

import de.orangestar.game.gameobjects.item.ItemType;

/**
 * Represents the ItemType Knife.
 * 
 * @author Oliver &amp; Basti
 */
public class Knife extends ItemType {

	public static final Knife Instance = new Knife();
	
	@Override
	public String getName() {
		return "Knife";
	}

	@Override
	public String getTexturePath() {
		return "textures/items/knife1.png";
	}

	@Override
	public float getWidth() {
		return 4;
	}

	@Override
	public float getHeight() {
		return 16;
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}

	private Knife() { }

}
