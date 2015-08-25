package de.orangestar.engine.render;

import java.util.Comparator;

import de.orangestar.engine.Component;

/**
 * The base class for render modules.
 * A gameobject can have a rendermodule. It's task is to visually represent the gameobject.
 * 
 * @author Oliver &amp; Basti
 */
public class RenderComponent extends Component {

	/**
     * Used to compare the rendering priority of two actors.
     * 
     * @author Basti
     */
    public static class PriorityComparer implements Comparator<RenderComponent> {
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
     * Implement component updates here. (Called on a frequently time by the corresponding manager)
     */
    public void onUpdate(IRenderEngine engine, Camera camera) { }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private int        _layer;
    
}
