package de.orangestar.engine.render;

import java.util.ArrayList;
import java.util.List;

import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Viewport4f;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Vector4f;

/**
 * An orthographic implementation of the camera interface. Typically used for 2D rendering.
 * 
 * @author Oliver &amp; Basti
 */
public class OrthographicCamera extends Camera {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    /**
     * Public-Constructor.
     */
    public OrthographicCamera() {        
        Vector3f eye = new Vector3f(0f, 0f, Float.MIN_VALUE);
        setPosition(eye);
        setViewport(0, 0, 0, 0);
    }
    
    @Override
    public void updateView() {
        // Don't clear the depth because everything should have z=0.0f;
        // Don't clear color, because the complete screen should be rerendered
        // GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        // Set the vire and projection matrix
        Matrix4f.lookAtLH(_eye, _target, Vector3f.Y_AXIS, _viewMatrix);
        Matrix4f.ortho2D(_viewport.left, _viewport.right, _viewport.bottom, _viewport.top, _projMatrix);
        
        getRenderEngine().setViewMatrix(_viewMatrix);
        getRenderEngine().setProjectionMatrix(_projMatrix);
    }
    
    /**
     * Returns the viewport of this camera in world units.
     * @return the viewport of this camera in world units
     */
    public Viewport4f getViewport() {
        return _viewport;
    }
    
    /**
     * Returns the viewport of this camera in world units coordinates.
     * @return the viewport of this camera in world units coordinates
     */
    public Viewport4f getWorldViewport() {
    	Viewport4f.set(_worldViewport, _viewport.left + _eye.x, _viewport.top + _eye.y, _viewport.right + _eye.x, _viewport.bottom + _eye.y );
        return _worldViewport;
    }

    /**
     * Sets the viewport of this camera.
     * @param left The x value of the left view space clipping plane
     * @param top The y value of the top view space clipping plane 
     * @param right The x value of the right view space clipping plane
     * @param bottom The y value of the bottom view space clipping plane 
     */
    public void setViewport(float left, float top, float right, float bottom) {
        setViewport(new Viewport4f(left, top, right, bottom));
    }
    
    /**
     * Sets the viewport of this camera.
     * @param rect The viewport
     */
    public void setViewport(Viewport4f rect) {
        _viewport = rect;
        
        notifyViewChanged();
    }
    
    /**
     * Returns the world position of the camera.
     * @return the world position of the camera
     */
    @Override
    public Vector3f getPosition() {
        return _eye;
    }
    
    /**
     * Sets the world position of this camera.
     * @param position the world position
     */
    @Override
    public void setPosition(Vector3f position) {
        // _eye = position
        _eye.x = position.x;
        _eye.y = position.y;
        _eye.z = position.z;
        
        // _target = _eye + Vector3f.ZAxis
        _target.x = position.x;
        _target.y = position.y;
        _target.z = position.z + 1f;
        
        notifyViewChanged();
    }
    
    @Override
	public Vector3f mouseScreenToWorld(float mx, float my) {
        float x =  2.0f * mx / getRenderEngine().getRenderWidth() - 1;
        float y = -2.0f * my / getRenderEngine().getRenderHeight() + 1;
        
        // We are inverting the multiplication of the viewMatrix and projectionMatrix ...
        Matrix4f.set(_screenToWorldMatrix, _viewMatrix);
        Matrix4f.mul(_screenToWorldMatrix, _projMatrix);
        Matrix4f.invert(_screenToWorldMatrix);
    
        // ... so we can map the 2D screen coordinates into the world
        Vector4f point = new Vector4f(x, y, 0, 1);
        Matrix4f.mul(_screenToWorldMatrix, point);
        
        
        System.out.println(point.x + "," + point.y);
        
        return point.toVector3f();
	}

    @Override
    public void addViewChangedListener(ViewChangedListener listener) {
        _listeners.add(listener);
    }

    @Override
    public void removeViewChangedListener(ViewChangedListener listener) {
        _listeners.remove(listener);
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Vector3f    _eye    = Vector3f.zero();
    private Vector3f    _target = Vector3f.zero();
    private Viewport4f  _viewport;
    private Viewport4f  _worldViewport = new Viewport4f(0, 0, 0, 0);

    // Instance reusing
    private Matrix4f _projMatrix          = Matrix4f.identity();
    private Matrix4f _viewMatrix          = Matrix4f.identity();
    private Matrix4f _screenToWorldMatrix = Matrix4f.identity();

    private List<ViewChangedListener> _listeners = new ArrayList<>();
    
    private void notifyViewChanged() {
        for(int i = 0; i < _listeners.size(); i++) {
            _listeners.get(i).onViewChanged(this);
        }
    }
}