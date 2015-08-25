package de.orangestar.engine.render.actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.orangestar.engine.render.IRenderEngine;
import de.orangestar.engine.render.RenderEngine;
import de.orangestar.engine.utils.Deinitializable;
import de.orangestar.engine.values.Anchor;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Transform;
import de.orangestar.engine.values.Vector3f;

/**
 * Actors are filling the gap between hardware-near batches and the engines GameObject-Component system.
 * Actors can be reused by multiple gameobjects because all they need to know is how to render themselfs at a 
 * given location.
 * 
 * @author Oliver &amp; Basti
 */
public abstract class Actor implements Deinitializable {
        
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor.
     */
    public Actor() {
        _isVisible = true;
        _anchor = Anchor.MID;
        _transform = new Transform();
        _children = new ArrayList<>();
    }

    /**
     * If this actor is visible in the scene.
     * @return If this actor is visible in the scene
     */
    public boolean isSceneVisible() {
        if (!isVisible()) {
            return false;
        }
        
        Actor actor = getParent();
        
        while( actor != null ) {
            if (!actor.isVisible()) {
                return false;
            }
            
            actor = actor.getParent();
        }
        
        return true;
    }
    
    /**
     * If this actor is set as visible.
     * @return If this actor is set as visible
     */
    public boolean isVisible() {
        return _isVisible;
    }
    
    /**
     * Set the visibility.
     * @param visible The visibility
     */
    public void setVisible(boolean visible) {
        _isVisible = visible;
    }
      
    /**
     * If the <i>actor</i> is a child of this instance.
     * @param actor A actor
     * @return If the <i>actor</i> is a child of this instance
     */
    public boolean isChild(Actor actor) {
        return _children.contains(actor);
    }
    
    /**
     * Adds a child actor to this one.<br>
     * Preconditions:
     * <ul>
     *  <li><code>child != null</code>
     *  <li><i>child</i> is not an ancestor of <i>this</i>
     * </ul>
     * @param child An actor
     */
    public void addChild(Actor child) {
        // Preconditions:
        assert child != null;
        assert !hasChildParentCycle(this, child);
        
        // Code:
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
     * Returns a readonly list of all children of this actor.
     * @return A readonly list of all children of this actor
     */
    // TODO: Convert readonly list to memberfield
    public List<Actor> getChildren() {
        return Collections.unmodifiableList( _children );
    }
    
    /**
     * Returns the parent actor of this.
     * @return The parent or null
     */
    public Actor getParent() {
        return _parent;
    }
        
    /**
     * Returns the transform that represents the local offset of this actor.
     * @return The transform that represents the local offset of this actor
     */
    public Transform getTransform() {
        return _transform;
    }
    
    /**
     * Sets the transform that represents the local offset of this actor.
     * @param transform The transform
     */
    public void setTransform(Transform transform) {
        Transform.set(_transform, transform);
    }
    
    /**
     * Sets the anchor of this image.
     * @param anchor The anchor
     */
    public void setAnchor(Anchor anchor) {
        _anchor = anchor;
    }
    
    /**
     * Returns the anchor of this image.
     * @return Returns the anchor
     */
    public Anchor getAnchor() {
        return _anchor;
    }

    /**
     * Renders this actor and then all it's children at the given transform.
     * @param parentTransform The transform
     */
    public void render(IRenderEngine engine, Transform parentTransform) {
        if (!_isVisible || _isDestroyed) {
            return;
        }

        Matrix4f  renderMatrix          = Matrix4f.POOL.get();
        Transform renderTransform       = Transform.POOL.get();
        Transform copyCurrentTransform  = Transform.POOL.get();
        
        // Combine parentTransform with local _transform
        Transform.set(renderTransform, parentTransform);
        Transform.set(copyCurrentTransform, _transform);
        anchorPosition(copyCurrentTransform.position, _anchor, copyCurrentTransform.scale);
        
        Transform.combine(renderTransform, copyCurrentTransform);
        
        // Set the world matrix (the actual opengl rendering location) and render
        Transform.toMatrix4f(renderTransform, renderMatrix);
        engine.setWorldMatrix(renderMatrix);
        onRender(engine);
        
        Matrix4f.POOL.release(renderMatrix);
        Transform.POOL.release(copyCurrentTransform);
        
        // Render the children actors
        for(int i = 0; i < _children.size(); i++) {
            _children.get(i).render(engine, renderTransform);
        }
        
        // Cleanup rendering
        Transform.POOL.release(renderTransform);
    }

    /**
     * Destroys this actor and frees all it used resources.
     */
    public void onDeinitialize() {
        onRelease();
        _isDestroyed = true;
    }
    
    /**
     * If this actor has been destroyed and is not usable.
     * @return If this actor has been destroyed and is not usable
     */
    public boolean isReleased() {
        return _isDestroyed;
    }

    /**
     * When this method is called, all used resources shall be freed and the actor shall clean himself up.
     */
    public abstract void        onRelease();

    /**
     * Implement the actual rendering code here.
     */
    public abstract void        onRender(IRenderEngine engine);
        
    /**
     * Return the width for anchoring. 
     * If the anchor is set to center, the positions x value is reduced by half of the width.
     * @return The anchoring width
     */
    public abstract float       getWidth();
    
    /**
     * Return the height. 
     * If the anchor is set to middle, the positions y value is
     * reduced by half of the anchoring height.
     * @return The anchoring width
     */
    public abstract float       getHeight();
    
    /**
     * Return the width for anchoring.
     * If the anchor is set to middle, the positions y value is reduced by half of the anchoring height.
     * @return The anchoring width
     */
    public abstract float       getDepth();
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private boolean     _isVisible;
    private Anchor      _anchor;

    private Transform   _transform;
    private Actor       _parent;
    private List<Actor> _children;
    private boolean     _isDestroyed;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                           Private Static                           */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Tries to find a parent-child cycle (the <i>child</i> is already a parent of <i>parent</i>).
     * @param parent The alleged parent actor
     * @param child The alleged child actor
     * @return If the <i>child</i> is already a parent of <i>parent</i>
     */
    private static boolean hasChildParentCycle(Actor parent, Actor child) {
        while(parent._parent != null) {
            if (parent._parent == child) {
                return true;
            }
        }
        
        return false;
    }
    
    private void anchorPosition(Vector3f out, Anchor anchor, Vector3f scale) {
        // X
        if (anchor.isXAxisCenter()) {
            out.x -= getWidth() * scale.x * 0.5f ;
        } else if (anchor.isRight()) {
            out.x -= getWidth() * scale.x;
        }
        
        // Y
        if (anchor.isYAxisCenter()) {
            out.y -= getHeight() * scale.y * 0.5f;
        } else if (anchor.isBottom()) {
            out.y -= getHeight() * scale.y;
        }
        
        // Z
        if (anchor.isZAxisCenter()) {
            out.z -= getDepth() * scale.z * 0.5f;
        } else if (anchor.isBottom()) {
            out.z -= getDepth() * scale.z;
        }
    }

}
