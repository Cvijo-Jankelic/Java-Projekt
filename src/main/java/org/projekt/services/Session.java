package org.projekt.services;

import org.projekt.entity.Admin;
import org.projekt.entity.AppUser;
import org.projekt.entity.CommonUser;

public class Session {
    private static AppUser currentUser;

    private Session() {} // Privatni konstruktor sprječava izravno instanciranje

    public static AppUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(AppUser user) {
        currentUser = user;
    }
}
