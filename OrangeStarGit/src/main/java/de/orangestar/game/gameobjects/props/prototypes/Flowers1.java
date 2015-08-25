package de.orangestar.game.gameobjects.props.prototypes;

import de.orangestar.engine.values.Anchor;
import de.orangestar.game.gameobjects.props.PropsType;

/**
 * The PropType for a flower.
 * 
 * @author Oliver &amp; Basti
 */
public class Flowers1 implements PropsType {

    public static final Flowers1 Instance = new Flowers1();

    @Override
    public String getTexturePath() {
        return "textures/props/flowers1.png";
    }

    @Override
    public float getWidth() {
        return 14;
    }

    @Override
    public float getHeight() {
        return 14;
    }

    @Override
    public Anchor getAnchor() {
        return Anchor.BOTTOM;
    }

    @Override
    public float getMinScale() {
        return 0.8f;
    }

    @Override
    public float getMaxScale() {
        return 1.2f;
    }

    @Override
    public float getGenerationProbability() {
        return 0.025f;
    }
    
    @Override
    public boolean isRotatable() {
        return true;
    }

    private Flowers1() { }
	
}
