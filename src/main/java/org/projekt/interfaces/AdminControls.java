package org.projekt.interfaces;

import org.projekt.entity.Admin;
import org.projekt.entity.AppUser;

import java.util.List;

public sealed interface AdminControls permits Admin {
    void removeUsersFromDataBase(List<AppUser> usersList);

}
