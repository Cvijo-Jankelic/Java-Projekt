package org.projekt.utils;

import org.projekt.entity.Ad;
import org.projekt.entity.Admin;
import org.projekt.entity.Campaign;
import org.projekt.entity.User;

import java.io.FileReader;
import java.io.IOException;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseUtils {

    private static final String DATABASE_FILE = "database-properties/database.properties";

    public static Connection connectionToDataBase() throws SQLException, IOException{
        Properties properties = new Properties();
        properties.load(new FileReader(DATABASE_FILE));
        String dataBaseUrl = properties.getProperty("databaseUrl");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        Connection connection = DriverManager.getConnection(dataBaseUrl, username, password);

        return connection;
    }


    public static List<Campaign> getCampaignsFromDataBase(){
        List<Campaign> campaignList = new ArrayList<>();


        return campaignList;

    }

    public static List<Ad> getAdsFromDataBase(){
        List<Ad> adList = new ArrayList<>();


        return adList;

    }

    public static List<User> getUsersFromDataBase(){
        List<User> usersList = new ArrayList<>();

        return usersList;
    }

    public static List<Admin> getAdminsFromDataBase(){
        List<Admin> adminList = new ArrayList<>();

        return adminList;

    }







}
