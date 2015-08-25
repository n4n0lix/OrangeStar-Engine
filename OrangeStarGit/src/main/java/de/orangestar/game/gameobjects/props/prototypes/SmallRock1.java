package de.orangestar.game.gameobjects.props.prototypes;

import de.orangestar.engine.values.Anchor;
import de.orangestar.game.gameobjects.props.PropsType;

/**
 * The PropType for a small rock.
 * 
 * @author Oliver &amp; Basti
 */
public class SmallRock1 implements PropsType {

    public static final SmallRock1 Instance = new SmallRock1();

    @Override
    public String getTexturePath() {
        return "textures/props/smallrock1.png";
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
        return Anchor.BOTTOM;
    }
    
    @Override
    public float getMinScale() { 
        return 0.5f;
    }

    @Override
    public float getMaxScale() {
        return 1.2f;
    }

    @Override
    public float getGenerationProbability() {
        return 0.005f;
    }
    
    @Override
    public boolean isRotatable() {
        return false;
    }
    
    private SmallRock1() { }

}
