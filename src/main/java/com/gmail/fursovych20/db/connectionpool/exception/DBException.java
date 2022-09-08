package com.gmail.fursovych20.db.connectionpool.exception;

/**
 * Exception class for JDBC
 */

public class DBException extends RuntimeException{

	private static final long serialVersionUID = 341167489014415017L;

	public DBException() {
		super();
	}

	public DBException(String message, Throwable cause) {
		super(message, cause);
	}

	public DBException(String message) {
		super(message);
	}

	public DBException(Throwable cause) {
		super(cause);
	}
	
	

}
