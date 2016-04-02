package net.github.rtc.app.exception;

/**
 * Service processing exception
 *
 * @author Vladislav Pikus
 */
public class ServiceProcessingException extends RuntimeException {

    public ServiceProcessingException() {
    }

    public ServiceProcessingException(final String message) {
        super(message);
    }
}