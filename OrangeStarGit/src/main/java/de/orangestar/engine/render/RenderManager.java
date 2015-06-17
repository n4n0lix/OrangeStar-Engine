package de.orangestar.engine.render;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.util.PriorityQueue;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import de.orangestar.engine.AbstractManager;
import de.orangestar.engine.GameObject;
import de.orangestar.engine.World;
import de.orangestar.engine.render.camera.Camera;
import de.orangestar.engine.render.component.RenderComponent;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Matrix4f.Order;
import de.orangestar.engine.values.Vector3f;

/**
 * Manager who organizes the visualisation of the game world onto the output display.
 * 
 * @author Basti
 */
public class RenderManager extends AbstractManager {

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                               PUBLIC                               */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	@Override
	public void start() {
	    // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        // Setup the mainwindow and display it in the center of the displays
        _mainWindow = new GLWindow();
        _mainWindow.setPostion(
                (GLFWvidmode.width(vidmode) - _mainWindow.getWidth()) / 2,
                (GLFWvidmode.height(vidmode) - _mainWindow.getHeight()) / 2);
        _mainWindow.show();
        
        // Make the OpenGL context current
        glfwMakeContextCurrent(_mainWindow.handle());        
        GLContext.createFromCurrent();
        
        // Setup settings of context
        setVSync(true);
        
        // Enable Alpha Blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

    @Override
    public void update() {
        // Clear the screen with black
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        GL11.glViewport(0, 0, _mainWindow.getRenderWidth(), _mainWindow.getRenderHeight());

        _camera.updateView();
        
        // Use a priority queue to sort all rendermodules by its rendering priority
        PriorityQueue<RenderComponent> renderingQueue = new PriorityQueue<>(10, new RenderComponent.RenderingPriorityComparer());
        
        for(GameObject obj : World.Get()) {
            if (obj.getRenderModule() != null) {
                renderingQueue.add(obj.getRenderModule());
            }
        }
        
        // Render
        for(RenderComponent module : renderingQueue) {
            module.onRender();
        }
                     
        // Display the rendered content
        glfwSwapBuffers(_mainWindow.handle());
    }
    
    @Override
    public boolean requestsExit() {
        return _mainWindow.closeRequested();
    }
	
	@Override
	public void shutdown() {
		_mainWindow.destroy();
		glfwTerminate();
	}

	/**
	 * Returns the main rendering window.
	 */
	public GLWindow getMainWindow() {
	    return _mainWindow;
	}
	
	public Camera getCamera() {
	    return _camera;
	}
	
	public void setActiveCamera(Camera camera) {
	    _camera = camera;
	}
	
	/**
	 * This enables or disables vsync.
	 */
	public void setVSync(boolean vSync) {
	    glfwSwapInterval(vSync ? 1 : 0);	    
	}
	
	public void setWireframe(boolean wireframe) {
	    if (wireframe) {
	        GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
            GL11.glPolygonMode(GL11.GL_BACK, GL11.GL_LINE);
	    } else {
	        GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
	        GL11.glPolygonMode(GL11.GL_BACK, GL11.GL_FILL); 
	    }
	}
	
	/**
	 * Returns the ByteBuffer that contains the WVP matrix.
	 */
	public ByteBuffer getWVPBuffer() {
        if (_wvpHasChanged) {
            
            _world_view_projection = _world.mul(_view).mul(_projection);
            
            _wvpBuffer = BufferUtils.createByteBuffer(16 * Float.BYTES);
            _world_view_projection.writeTo(_wvpBuffer, Order.COLUMN_MAJOR);
            _wvpBuffer.flip();
            
            _wvpHasChanged = false;
        }
	    
	    return _wvpBuffer.asReadOnlyBuffer();
	}
	
	/**
	 * Sets the world matrix that represents the transformation of the <i>World</i> 
	 * relatively to the absolute zero point in space.
	 */
	public void setWorldMatrix(Matrix4f matrix) {
	    _world = matrix;
	    _wvpHasChanged = true;
	}
	
	/**
	 * Sets the view matrix that positions the camera in the <i>World</i>.
	 */
    public void setViewMatrix(Matrix4f matrix) {
        _view = matrix;
        _wvpHasChanged = true;
    }
    
    public Matrix4f getViewMatrix() {
        return _view;
    }
    
    /**
     * Sets the projection matrix that transforms the orthogonal viewport of the <i>World</i>
     * into another projection (e.g. Perspective-View, Orthogonal-View)
     */
    public void setProjectionMatrix(Matrix4f matrix) {
        _projection = matrix;
        _wvpHasChanged = true;
    }
    
    public Matrix4f getProjectionMatrix() {
        return _projection;
    }
    
    public void setExtrapolation(float extrapolation) {
        _extrapolation = extrapolation;
    }
    
    /**
     * Used to calculate the position for rendering inbetween two logical steps
     * @return
     */
    public float getExtrapolation() {
        return _extrapolation;
    }

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              PRIVATE                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private GLWindow _mainWindow;
	private Camera   _camera;

    private float       _extrapolation;
    
    private boolean     _wvpHasChanged;
    private Matrix4f    _world = Matrix4f.one();
    private Matrix4f    _view = Matrix4f.one();
    private Matrix4f    _projection = Matrix4f.one();
    private Matrix4f    _world_view_projection;
    private ByteBuffer  _wvpBuffer;
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                              SINGLETON                             */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

	/**
	 * Returns the active instance of {@link RenderManager}
	 * @return A render manager
	 */
	public static RenderManager Get() {
		if (INSTANCE == null) {
			INSTANCE = new RenderManager();
		}

		return INSTANCE;
	}

	private static RenderManager INSTANCE = null;

}

