package de.orangestar.engine.render.component;

import java.util.Comparator;

import de.orangestar.engine.Component;
import de.orangestar.engine.GameObject;
import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.render.actor.Actor;
import de.orangestar.engine.values.Transform;

/**
 * The base class for render modules.
 * A gameobject can have a rendermodule. It's task is to visually represent the gameobject.
 * 
 * @author Basti
 */
public abstract class RenderComponent extends Component {

    public RenderComponent(GameObject obj) {
		super(obj);
	}

	/**
     * Used to compare the rendering priority of two actors.
     * 
     * @author Basti
     */
    public static class RenderingPriorityComparer implements Comparator<RenderComponent> {
        @Override
        public int compare(RenderComponent o1, RenderComponent o2) {
            return Integer.compare(o1.getLayer(), o2.getLayer());
        }
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
        
    /**
     * Sets on which layer the root actor is rendered. Use this to control which actor
     * is displayed in front/back of other actors.
     * @param layer The layer
     */
    public void setLayer(int layer) {
        _layer = layer;
    }
    
    /**
     * Returns the layer on which the root actor is rendered.
     * @return A layer id
     */
    public int getLayer() {
        return _layer;
    }
        
    /**
     * Renders this rendermodule. (Called every frame from the {@link RenderManager})
     */
    public abstract void onRender();

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private int   _layer;
    
}
