package de.orangestar.game.gameobjects.tree;

import java.util.Comparator;

import de.orangestar.engine.render.Texture;
import de.orangestar.engine.render.actor.InstancedImage;
import de.orangestar.engine.render.component.UnitRenderComponent;
import de.orangestar.engine.values.Transform;
import de.orangestar.game.MainGameState;

/**
 * The {@link de.orangestar.engine.render.RenderComponent} implementation of the {@link TreeModel}.
 * 
 * @author Oliver &amp; Basti
 */
public class TreeModelRenderComponent extends UnitRenderComponent {
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    @Override
    protected void onInitialize() {
        super.onInitialize();

        _actor = new InstancedImage.Builder()
                                        .texture(new Texture("textures/trees/tree1.png", true))
                                        .size(TreeFlyweight.WIDTH, TreeFlyweight.HEIGHT)
                                        .anchor(TreeFlyweight.ANCHOR)
                                        .sorted(Depth2DComparator)
                                        .build();

        addActor(_actor);
        setLayer(MainGameState.LAYER_VEGETATION);
    }
    
    @Override
    protected void onDeinitialize() {
        super.onDeinitialize();
        
        removeActor(_actor);
        _actor.onDeinitialize();
        _actor = null;
    }

    /**
     * Adds a flyweight to the model.
     * @param flyweight A flyweight
     */
    public void addFlyweight(TreeFlyweight flyweight) {
        _actor.addInstance(flyweight.getGlobalTransform());
    }
    
    public void removeFlyweight(TreeFlyweight flyweight) {
        _actor.removeInstance(flyweight.getGlobalTransform());
    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private InstancedImage _actor;

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Is used to sort the tree images, so that trees with higher y-values, are rendered in front of others.
     */
    private Comparator<Transform> Depth2DComparator = new Comparator<Transform>() {
        @Override
        public int compare(Transform o1, Transform o2) {
            return Float.compare(o1.position.y, o2.position.y);
        }
    };
}
