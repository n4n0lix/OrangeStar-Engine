package de.orangestar.game.gameobjects.props.prototypes;

import de.orangestar.engine.values.Anchor;
import de.orangestar.game.gameobjects.props.PropsType;

/**
 * The PropType for a bush.
 * 
 * @author Oliver &amp; Basti
 */
public class Bush2 implements PropsType {

    public static final Bush2 Instance = new Bush2();

    @Override
    public String getTexturePath() {
        return "textures/props/bush2.png";
    }

    @Override
    public float getWidth() {
        return 24;
    }

    @Override
    public float getHeight() {
        return 24;
    }

    @Override
    public Anchor getAnchor() {
        return Anchor.BOTTOM;
    }

    @Override
    public float getMinScale() {
        return 1f;
    }

    @Override
    public float getMaxScale() {
        return 1f;
    }

    @Override
    public float getGenerationProbability() {
        return 0.005f;
    }
    
    @Override
    public boolean isRotatable() {
        return false;
    }
    
    private Bush2() { }
	
}
