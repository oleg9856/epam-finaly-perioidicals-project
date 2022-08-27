package com.gmail.fursovych20.db.connectionpool.exception;

/**
 * Exception class for JDBC
 */

public class JDBCException extends RuntimeException{

	private static final long serialVersionUID = 341167489014415017L;

	public JDBCException() {
		super();
	}

	public JDBCException(String message, Throwable cause) {
		super(message, cause);
	}

	public JDBCException(String message) {
		super(message);
	}

	public JDBCException(Throwable cause) {
		super(cause);
	}
	
	

}
