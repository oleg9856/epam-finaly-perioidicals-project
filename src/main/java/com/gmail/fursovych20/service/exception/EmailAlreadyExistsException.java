package com.gmail.fursovych20.service.exception;

/**
 * An Email already exists exception
 *
 * @author O.Fursovych
 */
public class EmailAlreadyExistsException extends ServiceException {
	
    private static final long serialVersionUID = -7863971022455229516L;

    public EmailAlreadyExistsException() {
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
