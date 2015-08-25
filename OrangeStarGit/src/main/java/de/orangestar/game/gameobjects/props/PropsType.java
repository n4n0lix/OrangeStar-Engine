package de.orangestar.game.gameobjects.props;

import de.orangestar.engine.values.Anchor;

/**
 * PropType Interface for the prototypes.
 * 
 * @author Oliver &amp; Basti
 */
public interface PropsType {

	/**
	 * 
	 * @return the texture the prop uses
	 */
	public String   getTexturePath();
	
	/**
	 * 
	 * @return the width as a float of the prop
	 */
	public float	getWidth();
	
	/**
	 * 
	 * @return the height as a float of the prop
	 */
	public float	getHeight();
	
	/**
	 * 
	 * @return the Anchor, where the prop is located on the tile
	 */
	public Anchor   getAnchor();
	
	/**
	 * 
	 * @return if the prop is rotatable or not
	 */
	public boolean  isRotatable();
	
	/**
	 * 
	 * @return the mininum scale for the prop as a float
	 */
	public float    getMinScale();

	/**
	 * 
	 * @return the maximum scale for the prop as a float
	 */
	public float    getMaxScale();
	
	/**
	 * 
	 * @return the probability to spawn the Prop as a float
	 */
	public float    getGenerationProbability();
	
}
