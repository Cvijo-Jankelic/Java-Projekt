package org.projekt.exceptions;

public class CampaignNotFoundException extends RuntimeException{
    public CampaignNotFoundException(String message) {
        super(message);
    }

    public CampaignNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CampaignNotFoundException(Throwable cause) {
        super(cause);
    }
}
