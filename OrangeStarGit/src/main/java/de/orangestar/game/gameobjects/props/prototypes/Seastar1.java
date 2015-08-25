package de.orangestar.game.gameobjects.props.prototypes;

import de.orangestar.engine.values.Anchor;
import de.orangestar.game.gameobjects.props.PropsType;

/**
 * The PropType for a seastar.
 * 
 * @author Oliver &amp; Basti
 */
public class Seastar1 implements PropsType {

    public static final Seastar1 Instance = new Seastar1();

    @Override
    public String getTexturePath() {
        return "textures/props/seastar1.png";
    }

    @Override
    public float getWidth() {
        return 6;
    }

    @Override
    public float getHeight() {
        return 6;
    }

    @Override
    public Anchor getAnchor() {
        return Anchor.MID;
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
        return 0.05f;
    }
    
    @Override
    public boolean isRotatable() {
        return true;
    }
    
    private Seastar1() { }

}
