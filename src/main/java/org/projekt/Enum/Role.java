package org.projekt.Enum;

public enum Role {
    ADMIN, COMMON;

    public static Role transformFromStringToEnum(String roleStr){
        for(Role role : Role.values()){
            if(role.name().equalsIgnoreCase(roleStr)){
                return role;
            }
        }
        throw new IllegalArgumentException("No role with name " + roleStr + " found");
    }

}
