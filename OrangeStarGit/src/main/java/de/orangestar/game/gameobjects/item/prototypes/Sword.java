package de.orangestar.game.gameobjects.item.prototypes;

import de.orangestar.game.gameobjects.item.ItemType;

/**
 * Represents the ItemType Sword.
 * 
 * @author Oliver &amp; Basti
 */
public class Sword extends ItemType{

	public static final Sword Instance = new Sword();
	
	@Override
	public String getName() {
		return "Sword";
	}

	@Override
    public String getTexturePath() {
		return "textures/items/sword1.png";
	}

	@Override
	public float getWidth() {
		return 10;
	}

	@Override
	public float getHeight() {
		return 20;
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}

	private Sword() { }
	
}
