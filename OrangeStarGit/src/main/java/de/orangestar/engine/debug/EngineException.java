package de.orangestar.engine.debug;

public class EngineException extends RuntimeException {

    private static final long serialVersionUID = -4878444553674955311L;

    /**
     * @see RuntimeException
     */
    public EngineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @see RuntimeException
     */
    public EngineException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @see RuntimeException
     */
    public EngineException(String message) {
        super(message);
    }

    /**
     * @see RuntimeException
     */
    public EngineException(Throwable cause) {
        super(cause);
    }

}
