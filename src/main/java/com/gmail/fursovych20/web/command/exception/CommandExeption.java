package com.gmail.fursovych20.web.command.exception;

public class CommandExeption extends Exception{
    public CommandExeption() {
        super();
    }

    public CommandExeption(String message) {
        super(message);
    }

    public CommandExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandExeption(Throwable cause) {
        super(cause);
    }

    protected CommandExeption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
