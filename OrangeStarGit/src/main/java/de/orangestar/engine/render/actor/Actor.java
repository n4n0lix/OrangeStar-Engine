package de.orangestar.engine.render.actor;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import de.orangestar.engine.debug.DebugManager;
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
        _transform = new Transform();
        _children = new ArrayList<>();
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
        
    /**
     * Adds a child actor to this one.
     * @param child An actor
     */
    public void addChild(Actor child) {
        if (child._parent != null) {
            DebugManager.Get().info(Actor.class, "Can't link child to parent, because child already has a parent!");
            return;
        }
        
        _children.add(child);
        child._parent = this;
    }
    
    /**
     * Removes a child actor to this one.
     * @param child An actor
     */
    public void removeChild(Actor child) {
        _children.add(child);
        child._parent = null;
    }
    
    /**
     * Returns all children of this actor.
     */
    public List<Actor> getChildren() {
        return new ArrayList<>( _children );
    }
    
    /**
     * Returns the parent actor of this.
     * @return The parent or null
     */
    public Actor getParent() {
        return _parent;
    }
        
    public Transform getTransform() {
        return _transform;
    }
    
    public void setTransform(Transform transform) {
        _transform = transform;
    }
    
    /**
     * Renders this actor and then all it's children.
     * @param parentTransform
     */
    public void render(Transform parentTransform) {
        if (!_isVisible) {
            return;
        }

        Transform transform = parentTransform.combine(_transform);
        
        RenderManager.Get().setWorldMatrix(transform.toMatrix4f());
        onRender();
        
        for(Actor child : _children) {
            child.render(transform);
        }
    }

    public abstract void        onRender();

    public abstract void        onDestroy();
        
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private boolean     _isVisible;

    private Transform   _transform;
    private Actor       _parent;
    private List<Actor> _children;
    
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
