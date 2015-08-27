package de.orangestar.engine.render;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import java.nio.IntBuffer;

import de.orangestar.engine.utils.BufferPool;
import de.orangestar.engine.utils.Deinitializable;

/**
 * A basic rendering window. It wraps a GLFW-Window and its handling.
 * 
 * @author Oliver &amp; Basti
 */
public class GLWindow implements Deinitializable {

	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	/**
	 * Public-Constructor
	 */
	public GLWindow() {
        // Configure the window
        glfwDefaultWindowHints();
        
        glfwWindowHint( GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE );
        glfwWindowHint( GLFW_CONTEXT_VERSION_MAJOR, 3 );
        glfwWindowHint( GLFW_CONTEXT_VERSION_MINOR, 3 );
        glfwWindowHint( GLFW_VISIBLE, GL_FALSE );
        glfwWindowHint( GLFW_RESIZABLE, GL_TRUE );

        // Create the window
        _handle = glfwCreateWindow( 1200, 800, "", NULL, NULL );
        if ( _handle == NULL ) {
        	throw new RuntimeException( "Failed to create the GLFW window" );
        }
        
        setTitle( "OrangeStar Engine - #indev" );
	}
	
    /**
     * Does the OS dependent event handling for all windows of this engine instance.
     */
    public void doGuiEvents() {
        glfwPollEvents();
    }
    
	/**
	 * Sets the position of the window.
	 * @param x The x coordiante
	 * @param y The y coordiante
	 */
	public void setPostion(int x, int y) {
        glfwSetWindowPos( _handle, x, y );
	}
	
	/**
	 * Sets the size of this window.
	 * @param width The width
	 * @param height The height
	 */
	public void setSize(int width, int height) {
	    glfwSetWindowSize( _handle, width, height );
	}

	/**
	 * Returns the current width.
	 * @return The width of this window
	 */
	public int getWidth() {
	    int width;
	    IntBuffer widthBuffer  = BufferPool.getInt();
	    IntBuffer heightBuffer = BufferPool.getInt();
	    
	    glfwGetWindowSize( _handle, widthBuffer, heightBuffer );
	    
	    width = widthBuffer.get();
	    
	    BufferPool.releaseInt( widthBuffer );
	    BufferPool.releaseInt( heightBuffer );
	    
	    return width;
	}
	
    /**
     * Returns the current height.
     * @return The height of this window
     */
	public int 	getHeight() {
	    int height;
	    IntBuffer widthBuffer  = BufferPool.getInt();
	    IntBuffer heightBuffer = BufferPool.getInt();
	    
        glfwGetWindowSize( _handle, widthBuffer, heightBuffer );
        
        height = heightBuffer.get();
        
        BufferPool.releaseInt( widthBuffer );
        BufferPool.releaseInt( heightBuffer );
        
        return height;
	}
	
    /**
     * Returns the current rendering width.
     * @return The rendering width of this window
     */
    public int  getRenderWidth() {
        int width;
        IntBuffer widthBuffer  = BufferPool.getInt();
        IntBuffer heightBuffer = BufferPool.getInt();
        
        glfwGetFramebufferSize( _handle, widthBuffer, heightBuffer );
        
        width = widthBuffer.get();
        
        BufferPool.releaseInt( widthBuffer );
        BufferPool.releaseInt( heightBuffer );
        
        return width;
    }
    
    /**
     * Returns the current rendering height.
     * @return The rendering height of this window
     */
    public int  getRenderHeight() {
        int height;
        IntBuffer widthBuffer  = BufferPool.getInt();
        IntBuffer heightBuffer = BufferPool.getInt();
        
        glfwGetFramebufferSize( _handle, widthBuffer, heightBuffer );
        
        height = heightBuffer.get();
        
        BufferPool.releaseInt( widthBuffer );
        BufferPool.releaseInt( heightBuffer );
        
        return height;
    }
    
    /**
     * Sets the title.
     * @param title The title
     */
    public void setTitle(String title) {
        _title = title;
        glfwSetWindowTitle( _handle, _title );
    }
    
    /**
     * Returns the title.
     * @return The title
     */
    public String getTitle() {
        return _title;
    }
	
    /**
     * Makes this window visible.
     */
	public void show() {
        glfwShowWindow( _handle );
	}
	
	/**
	 * Returns the glfw-handle.
	 * @return The glfw handle
	 */
	public long handle() {
		return _handle;
	}
	
    @Override
    public void onDeinitialize() {
        glfwDestroyWindow( _handle );
    }

	/**
	 * If the X-Button has been clicked.
	 * @return If the closing of this window has been requested
	 */
	public boolean closeRequested() {
	    return glfwWindowShouldClose( _handle ) == GL_TRUE;
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

    private long        _handle;
    private String		_title;

}
