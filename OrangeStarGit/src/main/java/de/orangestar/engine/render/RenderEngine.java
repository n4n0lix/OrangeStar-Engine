package de.orangestar.engine.render;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.system.MemoryUtil;

import de.orangestar.engine.debug.Logger;
import de.orangestar.engine.debug.Logger.Level;
import de.orangestar.engine.values.Matrix4f;

/**
 * Manager who organizes the visualisation of the game world onto the output display.
 * 
 * @author Oliver &amp; Basti
 */
public class RenderEngine implements IRenderEngine {

    static { Logger.setLogging(RenderEngine.class, Level.DEBUG); }
    
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                               Public                               */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	@Override
	public void onStart() {
	    // Setup debugging
        glfwSetErrorCallback(_glfwErrorCallback = new GLFWErrorCallback() {
            @Override
            public void invoke(int error, long description) {
                Logger.error(RenderEngine.class, error + " - " + MemoryUtil.memDecodeUTF8(description));
            }
        });

	    // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        int vidModeWidth  = GLFWvidmode.width(vidmode);
        int vidModeHeight = GLFWvidmode.height(vidmode);
        
        // Setup the mainwindow and display it in the center of the displays
        _mainWindow = new GLWindow();
        _mainWindow.setPostion(
                (vidModeWidth  - _mainWindow.getWidth()) / 2,
                (vidModeHeight - _mainWindow.getHeight()) / 2);
        _mainWindow.show();
        
        // Make the OpenGL context current
        glfwMakeContextCurrent(_mainWindow.handle());        
        GLContext.createFromCurrent();
        
        // Setup settings of context
        setVSync(true);
        
        // Disable wireframe mode
        setWireframe(false);
        
        // Enable Alpha Blending
        setAlphablending(true);
        
        // Hints
        GL11.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_FASTEST);
        
        // Culling
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glFrontFace(GL11.GL_CCW);
	}

    @Override
    public void onUpdate() {
        GL11.glViewport(0, 0, _mainWindow.getRenderWidth(), _mainWindow.getRenderHeight());
        
        // Render
        Collections.sort(_cameras, _cameraSorter);
        for(Camera camera : _cameras) {
            camera.updateView();
            camera.render();
        }
                     
        // Display the rendered content
        glfwSwapBuffers(_mainWindow.handle());
    }
	
	@Override
	public void onShutdown() {
		_mainWindow.onDeinitialize();
        _glfwErrorCallback.release();
		glfwTerminate();
	}
	
	@Override
	public boolean requestsExit() {
	    return _mainWindow.closeRequested();
	}

	/**
	 * Returns the main rendering window.
	 * @return The main rendering window
	 */
	public long getGLFWWindowHandle() {
	    return _mainWindow.handle();
	}
	
	@Override
	public int getRenderWidth() {
	    return _mainWindow.getRenderWidth();
	}
	
	@Override
	public int getRenderHeight() {
	    return _mainWindow.getRenderHeight();
	}
	
	/**
	 * Sets the active camera.
	 * @param camera A camera
	 */
	@Override
	public void addActiveCamera(Camera camera) {
	    _cameras.add(camera);
	    camera.setRenderEngine(this);
	}
	
	/**
	 * If V-Sync is enabled.
	 * @return If V-Sync is enabled
	 */
	@Override
	public boolean isVSyncEnabled() {
	    return _vsync;
	}
	
	/**
	 * Enables or disables vsync.
	 * @param vSync If vsync is enabled
	 */
	@Override
	public void setVSync(boolean vSync) {
	    _vsync = vSync;
	    glfwSwapInterval(_vsync ? 2 : 1);	    
	}
	
	/**
	 * If the wireframe mode is enabled.
	 * @return If the wireframe mode is enabled
	 */
	@Override
	public boolean isWireframeEnabled() {
	    return _wireframe;
	}
	
	/**
	 * Enables or disables wireframe mode.
	 * @param wireframe If the wireframe mode is enabled
	 */
	@Override
	public void setWireframe(boolean wireframe) {
	    _wireframe = wireframe;
	    
	    if (_wireframe) {
	        GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE);
            GL11.glPolygonMode(GL11.GL_BACK, GL11.GL_LINE);
	    } else {
	        GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_FILL);
	        GL11.glPolygonMode(GL11.GL_BACK, GL11.GL_FILL); 
	    }
	}
	
	/**
	 * If alpha blending is enabled.
	 * @return If alpha blending is enabled
	 */
	@Override
	public boolean isAlphablending() {
	    return _alphaBlending;
	}
	
	/**
	 * Enables or disables alpha blending.
	 * @param alphablending If alpha blending is enabled
	 */
	@Override
	public void setAlphablending(boolean alphablending) {
	    _alphaBlending = alphablending;
	    
	    if (_alphaBlending) {
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	    } else {
	        GL11.glDisable(GL11.GL_BLEND);
	    }
        
	}
	
	/**
	 * Returns the ByteBuffer that contains the WVP matrix.
	 * @return The ByteBuffer that contains the WVP matrix
	 */
	@Override
	public ByteBuffer getWVPBuffer() {
        if (_wvpHasChanged) {
            
            Matrix4f.set(_worldViewProjection, _world);
            Matrix4f.mul(_worldViewProjection, _view);
            Matrix4f.mul(_worldViewProjection, _projection);
            
            if (_wvpBuffer == null) {
                _wvpBuffer = BufferUtils.createByteBuffer(16 * Float.BYTES);
            }
            
            _wvpBuffer.clear();
            _worldViewProjection.writeTo(_wvpBuffer);
            _wvpBuffer.flip();
            
            _wvpHasChanged = false;
        }
	    
	    return _wvpBuffer;
	}

	/**
	 * Sets the world matrix. This is used to position a rendering object in the <i>World</i> (OpenGL space).
	 * @param matrix The world matrix
	 */
	@Override
	public void setWorldMatrix(Matrix4f matrix) {
	    Matrix4f.set(_world, matrix);
	    _wvpHasChanged = true;
	}
	
	/**
	 * Returns the world matrix.
	 * @return The world matrix
	 */
	@Override
    public Matrix4f getWorldMatrix() {
        return _world;
    }
	
	/**
	 * Sets the view matrix. This is used to position the camera in the <i>World</i> (OpenGL space).
	 * @param matrix The view matrix
	 */
    @Override
    public void setViewMatrix(Matrix4f matrix) {
        Matrix4f.set(_view, matrix);
        _wvpHasChanged = true;
    }
        
    /**
     * Sets the projection matrix. This is used to transform the projection of the 3D-World onto the 2D-screen.
     * (e.g. Perspective-View, Orthogonal-View)
     * @param matrix The projection matrix
     */
    @Override
    public void setProjectionMatrix(Matrix4f matrix) {
        Matrix4f.set(_projection, matrix);
        _wvpHasChanged = true;
    }
    
    /**
     * Sets the current frame extrapolation.
     * @param extrapolation The extrapolation
     */
    @Override
    public void setExtrapolation(float extrapolation) {
        _extrapolation = extrapolation;
    }
    
    /**
     * Used to calculate the position for rendering in between two logical steps
     * @return The extrapolation
     */
    @Override
    public float getExtrapolation() {
        return _extrapolation;
    }


	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

	private GLWindow        _mainWindow;

	private List<Camera>            _cameras      = new ArrayList<>();
	private Camera.PriorityComparer _cameraSorter = new Camera.PriorityComparer();

	private boolean      _vsync;
	private boolean      _wireframe;
	private boolean      _alphaBlending;
	
    private float        _extrapolation;
    
    private boolean      _wvpHasChanged;
    private Matrix4f     _world                  = Matrix4f.identity();
    private Matrix4f     _view                   = Matrix4f.identity();
    private Matrix4f     _projection             = Matrix4f.identity();
    private Matrix4f     _worldViewProjection    = Matrix4f.identity();
    private ByteBuffer   _wvpBuffer;
    
    private GLFWErrorCallback    _glfwErrorCallback;
}

