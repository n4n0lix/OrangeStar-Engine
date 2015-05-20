package de.orangestar.engine.render;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GLContext;

import de.orangestar.engine.AbstractManager;
import de.orangestar.engine.logic.GameManager;
import de.orangestar.engine.logic.GameObject;
import de.orangestar.engine.logic.World;
import de.orangestar.engine.logic.modules.LogicModule;
import de.orangestar.engine.logic.modules.RenderModule;
import de.orangestar.engine.render.actor.ATileMap;
import de.orangestar.engine.render.batch.BatchFactory;
import de.orangestar.engine.render.batch.InstancingBatch;
import de.orangestar.engine.render.shader.Shader;
import de.orangestar.engine.resources.ResourceManager;
import de.orangestar.engine.values.Color4f;
import de.orangestar.engine.values.Matrix4f;
import de.orangestar.engine.values.Matrix4f.Order;
import de.orangestar.engine.values.Quaternion4f;
import de.orangestar.engine.values.Transform;
import de.orangestar.engine.values.Vector2f;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.engine.values.Vertex;

public class RenderManager extends AbstractManager {

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                               PUBLIC                               */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	@Override
	public void start() {
	    setWorldMatrix(Matrix4f.One);
	    setViewMatrix(Matrix4f.One);
	    setProjectionMatrix(Matrix4f.Ortho(-1.0f, 1.0f, -1.0f, 1.0f, 0, 1f));
	    
	    // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        _mainWindow = new GLWindow();
        _mainWindow.setPostion(
                (GLFWvidmode.width(vidmode) - _mainWindow.getWidth()) / 2,
                (GLFWvidmode.height(vidmode) - _mainWindow.getHeight()) / 2);
        _mainWindow.show();
        
        // Make the OpenGL context current
        glfwMakeContextCurrent(_mainWindow.handle());
        setBackbuffers(1);
        
        GLContext.createFromCurrent();
	}

    @Override
    public void update() {
        
        final float width  = _mainWindow.getRenderWidth();
        final float height = _mainWindow.getRenderHeight();
        
        glClearColor(0.4f, 0.7f, 1.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, _mainWindow.getRenderWidth(), _mainWindow.getRenderHeight());

        setWorldMatrix(Matrix4f.One);
        setViewMatrix(Matrix4f.One);
        setProjectionMatrix(Matrix4f.Ortho2D( 0, width, height, 0));

        RenderModule mod;
        for(GameObject obj : World.Get()) {
            if (obj._moduleRender != null) {
                obj._moduleRender.render();
            }
        }
        
//        offset += tW * GameManager.DELTA_TIME;
//        if (offset > 0) {
//            offset -= tW;
//        }
//
//        tilemap.render(new Transform(new Vector3f(offset, 0f, 0f), Vector3f.One, Quaternion4f.Identity));
        
//        RenderManager.Get().setWorldMatrix(new Transform(new Vector3f(offset, 0f, 0f), Vector3f.One, Quaternion4f.Identity).toMatrix4f());
//        instanceBatch.render(PrimitiveType.TRIANGLE_STRIP);
                
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
	 * Sets the number of used backbuffers. 1 = Double Buffering, 2 = Triple Buffering, ..., to avoid tearing.
	 */
	public void setBackbuffers(int numBackBuffers) {
	    glfwSwapInterval(numBackBuffers);	    
	}
	
	public ByteBuffer getWVPBuffer() {
        if (wvpChanged) {
            
            wvp = world.mul(view).mul(projection);
            
            wvpBuffer = BufferUtils.createByteBuffer(16 * Float.BYTES);
            wvp.writeTo(wvpBuffer, Order.COLUMN_MAJOR);
            wvpBuffer.flip();
            
            wvpChanged = false;
        }
	    
	    return wvpBuffer.asReadOnlyBuffer();
	}
	
	/**
	 * Sets the world matrix that represents the transformation of the <i>World</i> 
	 * relatively to the absolute zero point in space.
	 */
	public void setWorldMatrix(Matrix4f matrix) {
	    world = matrix;
	    wvpChanged = true;
	}
	
	/**
	 * Sets the view matrix that positions the camera in the <i>World</i>.
	 */
    public void setViewMatrix(Matrix4f matrix) {
        view = matrix;
        wvpChanged = true;
    }
    
    /**
     * Sets the projection matrix that transforms the orthogonal viewport of the <i>World</i>
     * into another projection (e.g. Perspective-View, Orthogonal-View)
     */
    public void setProjectionMatrix(Matrix4f matrix) {
        projection = matrix;
        wvpChanged = true;
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
    
	private float _extrapolation;
    
    private boolean  wvpChanged;
    private Matrix4f world;
    private Matrix4f view;
    private Matrix4f projection;
    private Matrix4f wvp;
    private ByteBuffer wvpBuffer;

    private ATileMap tilemap;
    private int tW = 32;
    private float offset = -tW;

}

