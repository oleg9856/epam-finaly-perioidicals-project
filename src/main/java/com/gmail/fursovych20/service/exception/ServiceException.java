package com.gmail.fursovych20.service.exception;

public class ServiceException extends Exception{

	private static final long serialVersionUID = -6167534899746513711L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}
	
	

}
