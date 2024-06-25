package org.projekt.exceptions;

public class NotAllowedNumberInThisSection extends RuntimeException{
    public NotAllowedNumberInThisSection(String message) {
        super(message);
    }

    public NotAllowedNumberInThisSection(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAllowedNumberInThisSection(Throwable cause) {
        super(cause);
    }
}
