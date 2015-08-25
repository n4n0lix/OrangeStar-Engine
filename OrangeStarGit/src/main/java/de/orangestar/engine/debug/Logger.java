package de.orangestar.engine.debug;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.lwjgl.opengl.GL11;

/**
 * Handles the debugging and logging of the engine.
 * 
 * @author Oliver &amp; Basti
 */
public class Logger {
	
    public static enum Level {
        SILENT, DEBUG, INFO, WARN, ERROR;
        
        private boolean isError() {
            return this == DEBUG || this == INFO || this == WARN || this == ERROR;
        }
        
        private boolean isWarn() {
            return this == DEBUG || this == INFO || this == WARN;
        }
        
        private boolean isInfo() {
            return this == DEBUG || this == INFO;
        }
        
        private boolean isDebug() {
            return this == DEBUG;
        }
    }
    
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                               Public                               */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

	/**
	 * Prints the newest OpenGL Error to the console.
	 * @param message A message to identify the error throwing code section.
	 */
	public static void checkGLError(String message) {
	    int errorValue = GL11.glGetError();
	    
        if (errorValue != GL11.GL_NO_ERROR) {
            throw new RuntimeException(message + " Error: " + errorValue);
        }
	}
	
	/**
	 * Enables the logging for a class on a specified debuglevel.
	 * @param clazz The class
	 * @param level The debug level
	 */
	public static void setLogging(Class<?> clazz, Level level) {
	    _loggingClasses.put(clazz, level);
	}
	
	/**
	 * Send a debug message.
	 * @param clazz The class from which the message was send
	 * @param message The message
	 */
	public static void debug(Class<?> clazz, String message) {
	    if (_loggingClasses.containsKey(clazz) && _loggingClasses.get(clazz).isDebug()) {
	        writeln("DEBUG - " + clazz.getSimpleName() + ".class : " + message);
	    }
	}
	
	/**
     * Send an info message.
     * @param clazz The class from which the message was send
     * @param message The message
     */
	public static void info(Class<?> clazz, String message) {
	    if (_loggingClasses.containsKey(clazz) && _loggingClasses.get(clazz).isInfo()) {
	        writeln("INFO - " + clazz.getSimpleName() + ".class : " + message);
	    }
	}
	
    /**
     * Send an warn message.
     * @param clazz The class from which the message was send
     * @param message The message
     */
    public static void warn(Class<?> clazz, String message) {
        if (_loggingClasses.containsKey(clazz) && _loggingClasses.get(clazz).isWarn()) {
            writeln("WARN - " + clazz.getSimpleName() + ".class : " + message);
        }
    }
	
    /**
     * Send an error message.
     * @param clazz The class from which the message was send
     * @param message The message
     */
    public static void error(Class<?> clazz, String message) {
        if (_loggingClasses.containsKey(clazz) && _loggingClasses.get(clazz).isError()) {
            writeln("ERROR - " + clazz.getSimpleName() + ".class : " + message);
        }
    }
    
    public static void addPrintStream(PrintStream stream) {
        _outStreams.add(stream);
    }
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	/*                              Private                               */
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

	private static Map<Class<?>, Level> _loggingClasses = new HashMap<Class<?>, Level>();
	private static List<PrintStream>    _outStreams = new ArrayList<>();
	
	private static void writeln(String message) {
	    for(PrintStream outStream : _outStreams) {
	        outStream.println(message);
	    }
	}

}
