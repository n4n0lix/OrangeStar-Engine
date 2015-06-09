package de.orangestar.engine.render.actor;

import org.lwjgl.opengl.GL11;

import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.values.Transform;

public abstract class Actor {

//    public static class Animation {
//        
//        public static final String Idle    = "anim_idle";
//        public static final String Walking = "anim_walking";
//        public static final String Running = "anim_running";
//        public static final String Jumping = "anim_jumping";
//        
//    }
//    
//    public class AnimPlayer {
//        
//        private String animName;
//        private float  animStep;
//        
//        /**
//         * Sets the new animation.
//         * @param name
//         */
//        public void startAnimation(String name) {
//            animName = name;
//            animStep = 0.0f;
//        }
//        
//    }
        
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public Actor() {
        _isVisible = true;
    }

    /**
     * If this actor is visible.
     */
    public boolean isVisible() {
        return _isVisible;
    }
    
    /**
     * Set the visibility.
     * @param visible 
     */
    public void setVisible(boolean visible) {
        _isVisible = visible;
    }
        
//    /**
//     * Adds a child actor to this one.
//     * @param child An actor
//     */
//    public void addChild(Actor child) {
//        linkParentWithChild(this, child);
//    }
//    
//    /**
//     * Removes a child actor to this one.
//     * @param child An actor
//     */
//    public void removeChild(Actor child) {
//        unlinkParentWithChild(this, child);
//    }
//    
//    /**
//     * Returns all children of this actor.
//     */
//    public List<Actor> getChildren() {
//        return new ArrayList<>( _children );
//    }
//    
//    /**
//     * Returns the parent actor of this.
//     * @return The parent or null
//     */
//    public Actor getParent() {
//        return _parent;
//    }
//    
//    /**
//     * Sets the parent of this actor.
//     * @param parent An actor
//     */
//    public void setParent(Actor parent) {
//        if (parent == null) {
//            unlinkParentWithChild(parent, this);
//            return;
//        }
//        
//        linkParentWithChild(parent, this);
//    }
    
    /**
     * Renders this actor and then all it's children.
     * @param transform
     */
    public void render(Transform transform) {
        if (!_isVisible) {
            return;
        }
//        System.out.println("Rendering at " + transform.position.toString());

        RenderManager.Get().setWorldMatrix(transform.toMatrix4f());
        onRender();
    }

    public abstract void        onRender();

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private boolean     _isVisible;

    
//    private Actor       _parent;
//    private List<Actor> _children;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
//    private static void linkParentWithChild(Actor parent, Actor child) {
//        if (child == null) {
//            return;
//        }
//
//        if (parent == null) {
//            return;
//        }
//
//        if (parent._children.contains(child)) {
//            return;
//        }
//        
//        if (child._parent != null) {
//            unlinkParentWithChild(child._parent, child);
//        }
//        
//        parent._children.add(child);
//        child._parent = parent;
//    }
//    
//    private static void unlinkParentWithChild(Actor parent, Actor child) {
//        if (child == null) {
//            return;
//        }
//
//        if (parent == null) {
//            return;
//        }
//        
//        if (!parent._children.contains(child)) {
//            return;
//        }
//        
//        parent._children.remove(child);
//        child._parent = null;
//    }

}
