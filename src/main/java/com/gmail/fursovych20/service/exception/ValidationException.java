package com.gmail.fursovych20.service.exception;

/**
 * Validation exception
 *
 * @author O.Fursovych
 */
public class ValidationException extends ServiceException {
    private static final long serialVersionUID = 5367379138882817091L;

    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
