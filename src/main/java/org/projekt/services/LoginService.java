package org.projekt.services;

import org.projekt.exceptions.SameNameException;
import org.projekt.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

public class LoginService {
    private static final String DATABASE_FILE = "database-properties/database.properties";
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    public static boolean checkLogin(String username, String password){

        try(Connection connection = DatabaseUtils.connectionToDataBase()){
            String sqlQuery = "SELECT password FROM users WHERE username = ?";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                String storedHashedPassword = rs.getString("password");

                String hashedPassword = hashPassword(password);

                if(storedHashedPassword.equals(hashedPassword)){
                    return true;
                }

            }

            return false;


        }catch (SQLException | IOException ex) {
            String message = "Dogodila se greska kod logiranja i pronalazenja sifre u bazi podataka!";
            logger.error(message, ex);
            System.out.println(message);
            return false;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void registerUser(String username) throws SameNameException, SQLException, IOException {

        try (Connection connection = DatabaseUtils.connectionToDataBase()) {

            String query = "SELECT COUNT(*) AS userCount FROM users WHERE username = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                pstmt.setString(1, username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt("userCount") > 0) {

                    throw new SameNameException("Korisnik s imenom '" + username + "' već postoji.");
                }
            }
        }
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(password.getBytes());

        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
