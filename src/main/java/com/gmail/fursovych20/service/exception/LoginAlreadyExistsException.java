package com.gmail.fursovych20.service.exception;

/**
 * Login already exists exception
 *
 * @author O.Fursovych
 */
public class LoginAlreadyExistsException extends ServiceException {
    private static final long serialVersionUID = 5367379138882817091L;

    public LoginAlreadyExistsException() {
    }

    public LoginAlreadyExistsException(String message) {
        super(message);
    }

    public LoginAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
