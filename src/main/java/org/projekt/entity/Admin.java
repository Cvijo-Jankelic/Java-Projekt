package org.projekt.entity;

import org.projekt.Enum.Role;
import org.projekt.interfaces.AdminControls;
import org.projekt.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public final class Admin extends AppUser implements AdminControls {

    private static final Logger logger = LoggerFactory.getLogger(Admin.class);

    public Admin(Integer id, String username, String password, Role role, LocalDateTime createdAt) {
        super(id, username, password, role, createdAt);
    }

    @Override
    public void removeUsersFromDataBase(List<AppUser> usersList) {

    }

    @Override
    public String toString() {
        return super.getUsername();
    }
}
