package de.orangestar.game.gameobjects.props.prototypes;

import de.orangestar.engine.values.Anchor;
import de.orangestar.game.gameobjects.props.PropsType;

/**
 * The PropType for a bush.
 * 
 * @author Oliver &amp; Basti
 */
public class Bush1 implements PropsType {

    public static final Bush1 Instance = new Bush1();

    @Override
    public String getTexturePath() {
        return "textures/props/bush1.png";
    }

    @Override
    public float getWidth() {
        return 18;
    }

    @Override
    public float getHeight() {
        return 16;
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

    private Bush1() { }
	
}
