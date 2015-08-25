package de.orangestar.engine.render;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;
import de.orangestar.engine.values.Vector3f;

/**
 * The interface for camera implementations.
 * 
 * @author Oliver &amp; Basti
 */
public abstract class Camera {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                            Public Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    /**
     * Used to compare the rendering priority of two actors.
     * 
     * @author Basti
     */
    public static class PriorityComparer implements Comparator<Camera> {
        @Override
        public int compare(Camera o1, Camera o2) {
            return Integer.compare(o1.getLayer(), o2.getLayer());
        }
    }
    
    /**
     * Listener that get notified if the corresponding camera has changed its view space.
     * 
     * @author Basti
     */
    public interface ViewChangedListener {
        
        /**
         * The view space of <i>source</i> has changed.
         * @param source The source of this event
         */
        public void onViewChanged(Camera source);

    }

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public void render() {
        if (_world == null) {
            return;
        }
        
        Queue<RenderComponent> renderingQueue = new PriorityQueue<>(new RenderComponent.PriorityComparer());
        
        for(GameObject obj : _world.getGameObjects()) {
            if (obj.getRenderComponent() != null) {              
                renderingQueue.add(obj.getRenderComponent());
            }
        }
        
        for(RenderComponent comp : renderingQueue) {
            comp.onUpdate(_engine, this);
        }
    }
    
    /**
     * Called upon the beginning of every frame if this is the active camera to update the view.
     */
    public abstract void     updateView();

    public abstract void     setPosition(Vector3f position);
    
    public abstract Vector3f getPosition();
    
    public void setLayer(int layer) {
        _layer = layer;
    }
    
    public int getLayer() {
        return _layer;
    }
    
    public void setWorld(World world) {
        _world = world;
    }
    
    public World getWorld() {
        return _world;
    }
    
    /**
     * Translates a screen point to a world coordinate of the camera.
     * @param x The x on the screen
     * @param y The y on the screen
     * @return The 3D point in the world
     */
    // TODO: Add depth argument for non orthographic cameras
    public abstract Vector3f mouseScreenToWorld(float x, float y);

    /**
     * Adds a {@link ViewChangedListener} to this instance.
     * @param listener The listener instance
     */
    public abstract void     addViewChangedListener(ViewChangedListener listener);

    /**
     * Removes a {@link ViewChangedListener} from this instance.
     * @param listener The listener instance
     */
    public abstract void     removeViewChangedListener(ViewChangedListener listener);

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Protected                             */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    protected IRenderEngine getRenderEngine() {
        return _engine;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Package                              */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    void setRenderEngine(IRenderEngine engine) {
        _engine = engine;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private int          _layer;
    private World        _world;
    private IRenderEngine _engine;
}
