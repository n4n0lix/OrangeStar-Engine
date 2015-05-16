package de.orangestar.engine.debug;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import de.orangestar.engine.AbstractManager;

/**
 * Handles the debugging and logging of the engine.
 * 
 * @author Basti
 */
public class DebugManager extends AbstractManager {
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                               PUBLIC                               */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    @Override
	public void start() {
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
	}
	
    @Override
    public void update() {
 
	}
	    
	@Override
	public void shutdown() {
		errorCallback.release();
	}
	
	/**
	 * Clears the OpenGL Error chache.
	 */
	public void glClearError() {
	    GL11.glGetError();
	}
	
	/**
	 * Prints the newest OpenGL Error to the console.
	 * @param message A message to identify the error throwing code section.
	 */
	public void glCheckError(String message) {
	    int errorValue = GL11.glGetError();
	    
        if (errorValue != GL11.GL_NO_ERROR) {
            System.err.println("ERROR - " + message + ": " + errorValue);
            System.exit(-1);
        }
	}
	
	public void debug(Class<?> clazz, String message) {
	    System.out.println("DEBUG - " + clazz.getSimpleName() + ".class : " + message);
	}
	
	public void info(Class<?> clazz, String message) {
	    System.out.println("INFO - " + clazz.getSimpleName() + ".class : " + message);
	}
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                              PRIVATE                               */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private GLFWErrorCallback errorCallback;
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                              SINGLETON                             */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

	/**
	 * Returns the active instance of {@link DebugManager}
	 * @return A debug manager
	 */
	public static DebugManager Get() {
		if (INSTANCE == null) {
			INSTANCE = new DebugManager();
		}
		
		return INSTANCE;
	}
	
	private static DebugManager INSTANCE = null;


}
