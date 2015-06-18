package de.orangestar.engine.render.camera;

import de.orangestar.engine.render.RenderManager;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Rectangle4f;
import de.orangestar.engine.values.Vector3f;

public class OrthographicCamera implements Camera {

    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public OrthographicCamera() {
        setViewport(0, 0, RenderManager.Get().getMainWindow().getWidth(), RenderManager.Get().getMainWindow().getHeight());
    }
    
    public void updateView() {
        RenderManager.Get().setViewMatrix(Matrix4f.lookAtLH(_eye, _eye.add(Vector3f.Z_AXIS), Vector3f.Y_AXIS));
        RenderManager.Get().setProjectionMatrix(Matrix4f.ortho2D(_viewport._x, _viewport._width, _viewport._height, _viewport._y));
    }
    
    public Rectangle4f getViewport() {
        return _viewport;
    }
    
    public void setViewport(Rectangle4f rect) {
        _viewport = rect;
    }
    
    public void setViewport(float left, float top, float right, float bottom) {
        setViewport(new Rectangle4f(left, top, right, bottom));
    }
    
    public Vector3f getEye() {
        return _eye;
    }
    
    public void setEye(Vector3f eye) {
        _eye = eye;
    }
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    private Vector3f    _eye = new Vector3f(0,0,0);
    private Rectangle4f _viewport;
}
