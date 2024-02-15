package org.projekt.exceptions;

public class SameNameException extends Exception{
    public SameNameException(String message) {
        super(message);
    }

    public SameNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public SameNameException(Throwable cause) {
        super(cause);
    }
}
