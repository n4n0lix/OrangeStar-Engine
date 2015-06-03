package de.orangestar.engine;


/**
 * The basic implementation for {@link Engine}-managers.
 * Becasuse it hides the public default constructor, the implementing classes are
 * forced to implement the singleton pattern.
 * 
 * @author Basti
 *
 */
public abstract class AbstractManager {

	/**
	 * Initializes and starts the manager.
	 */
	public abstract void start();
	
	/**
	 * Updates the managers state.
	 */
	public abstract void update();
	
	/**
	 * Deinitializes and stops the manager.
	 */
	public abstract void shutdown();
	
	/**
	 * Indicates if this Manager requests the application to terminate.
	 * It is not required for the application to comply to this request.
	 */
	public boolean requestsExit() {
	    return false;
	}
	
	/**
	 * Enforce protected Default-Constructor
	 */
	protected AbstractManager() { }
}
