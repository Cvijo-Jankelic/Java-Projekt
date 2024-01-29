package org.projekt.utils;

import org.projekt.Enum.Role;
import org.projekt.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.spi.CalendarDataProvider;

public class DatabaseUtils {

    private static final String DATABASE_FILE = "database-properties/database.properties";
    private static final Logger logger = LoggerFactory.getLogger(DatabaseUtils.class);

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
        try(Connection connection = connectionToDataBase()){
            String sqlQuery = "SELECT * FROM campaign";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Integer campaignId = rs.getInt("campaignID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String status = rs.getString("status");
                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");
                BigDecimal budget = rs.getBigDecimal("budget");
                String targetAudience = rs.getString("targetAudience");
                String channels = rs.getString("channels");
                BigDecimal roi = rs.getBigDecimal("ROI");
                Integer createdByID = rs.getInt("createdBy");

                Campaign campaign = new Campaign(campaignId, name, description,
                        status, startDate, endDate, budget, targetAudience, channels, roi, createdByID);

                campaignList.add(campaign);

            }
        }
        catch (SQLException | IOException ex) {
            String message = "Dogodila se greska kod dohvacanja podataka kampanje sa baze podataka!";
            logger.error(message, ex);
            System.out.println(message);
        }
        return campaignList;
    }

    public static List<Ad> getAdsFromDataBase(){
        List<Ad> adList = new ArrayList<>();

        try(Connection connection = connectionToDataBase()) {
            String sqlQuery = "SELECT * FROM ad";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Integer id = rs.getInt("adID");
                String name = rs.getString("name");
                String content = rs.getString("content");
                String type = rs.getString("type");
                String status = rs.getString("status");
                String targetAudience = rs.getString("targetAudience");
                Date startDate = rs.getDate("startDate");
                Date endDate = rs.getDate("endDate");
                Integer campaignId = rs.getInt("campaignID");
                Integer impression = rs.getInt("impressions");
                Long clicks = rs.getLong("clicks");
                Integer conversions = rs.getInt("conversions");

                Ad ad = new Ad(id, name, content, type, status, targetAudience,
                        startDate, endDate, campaignId, impression, clicks, conversions);

                adList.add(ad);
            }


        } catch (SQLException | IOException ex) {
            String message = "Dogodila se greska kod dohvacanja reklama sa baze podataka!";
            logger.error(message, ex);
            System.out.println(message);
        }

        return adList;

    }

    public static List<AppUser> getAppUsersFromDataBase(){
        List<AppUser> appUsersList = new ArrayList<>();

        try(Connection connection = connectionToDataBase()) {
            String sqlQuery = "SELECT * FROM users";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                Integer userId = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String roleStr = rs.getString("role");
                Role role = Role.transformFromStringToEnum(roleStr);
                LocalDateTime created_at = rs.getObject("created_at", LocalDateTime.class);
                try{
                    if(role == Role.ADMIN){
                        AppUser adminUser = new Admin(userId, username, password, role, created_at);
                        appUsersList.add(adminUser);
                    }else if(role == Role.COMMON_USER){
                        AppUser commonUser = new CommonUser(userId, username, password, role, created_at);
                        appUsersList.add(commonUser);
                    }else{
                        throw new IllegalArgumentException("Unknown role: " + roleStr);
                    }
                }catch (IllegalArgumentException ex){
                    logger.info("Pogreska " + ex.getMessage(), ex);
                    logger.trace(ex.getMessage(), ex);
                    // jos doraditi malo
                }

            }

        } catch (SQLException | IOException ex) {
            String message = "Dogodila se greska kod dohvacanja korisnika sa baze podataka!";
            logger.error(message, ex);
            System.out.println(message);
        }

        return appUsersList;
    }

    public static List<Company> getCompaniesFromDataBase(){
        List<Company> companies = new ArrayList<>();

        try(Connection connection = connectionToDataBase()){
            String sqlQuery = "SELECT * FROM tvrtke";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer id = rs.getInt("idtvrtke");
                String name = rs.getString("nazivTvrtke");
                String address = rs.getString("adresaTvrtke");
                String contact = rs.getString("kontaktInformacije");
                Company company = new Company(id, name, address, contact);

                companies.add(company);
            }


        }catch (SQLException | IOException ex) {
            String message = "Dogodila se greska kod dohvacanja kompanija sa baze podataka!";
            logger.error(message, ex);
            System.out.println(message);
        }
        return companies;
    }







}
