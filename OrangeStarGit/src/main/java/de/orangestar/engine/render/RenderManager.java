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
import de.orangestar.engine.render.component.RenderComponent;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Matrix4f.Order;

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
        setVSync(false);
        
        // Enable Alpha Blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); 
	}

    @Override
    public void update() {
        // Clear the screen with black
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        // Adjust the viewport (to match the current window size etc)
        final float width  = _mainWindow.getRenderWidth();
        final float height = _mainWindow.getRenderHeight();
        GL11.glViewport(0, 0, _mainWindow.getRenderWidth(), _mainWindow.getRenderHeight());

        
        setWorldMatrix(Matrix4f.One);
        setViewMatrix(Matrix4f.One);
        setProjectionMatrix(Matrix4f.ortho2D( 0, width, height, 0)); // Setup basic 2D orthographical view

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
	
	/**
	 * This enables or disables vsync.
	 */
	public void setVSync(boolean vSync) {
	    glfwSwapInterval(vSync ? 2 : 1);	    
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
    
    /**
     * Sets the projection matrix that transforms the orthogonal viewport of the <i>World</i>
     * into another projection (e.g. Perspective-View, Orthogonal-View)
     */
    public void setProjectionMatrix(Matrix4f matrix) {
        _projection = matrix;
        _wvpHasChanged = true;
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

    private float       _extrapolation;
    
    private boolean     _wvpHasChanged;
    private Matrix4f    _world;
    private Matrix4f    _view;
    private Matrix4f    _projection;
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

