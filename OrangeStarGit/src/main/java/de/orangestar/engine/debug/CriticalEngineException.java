package de.orangestar.engine.debug;

public class CriticalEngineException extends RuntimeException {

    private static final long serialVersionUID = -9100715766963130043L;

    /**
     * @see RuntimeException
     */
    public CriticalEngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @see RuntimeException
     */
    public CriticalEngineException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @see RuntimeException
     */
    public CriticalEngineException(String message) {
        super(message);
    }

    /**
     * @see RuntimeException
     */
    public CriticalEngineException(Throwable cause) {
        super(cause);
    }
    
}
