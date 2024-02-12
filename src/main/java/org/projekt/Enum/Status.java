package org.projekt.Enum;

public enum Status {
    AKTIVNA, PAUZA, STOPIRANA;
    public static Status transformFromStringToEnum(String statusStr){
        for(Status status : Status.values()){
            if(status.name().equalsIgnoreCase(statusStr)){
                return status;
            }
        }
        throw new IllegalArgumentException("No role with name " + statusStr + " found");
    }
}
