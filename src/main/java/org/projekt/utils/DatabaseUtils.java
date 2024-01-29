package org.projekt.utils;

import org.projekt.Enum.Role;
import org.projekt.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                BigDecimal budget = rs.getBigDecimal("budget");
                String targetAudience = rs.getString("targetAudience");
                String channels = rs.getString("channels");
                BigDecimal roi = rs.getBigDecimal("ROI");
                Integer createdByID = rs.getInt("createdBy");
                Integer companyId = rs.getInt("tvrtkaId");

                Campaign campaign = new Campaign(campaignId, name, description,
                        status, startDate, endDate, budget, targetAudience, channels, roi, createdByID, companyId);

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
                    }else if(role == Role.COMMON){
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

    public static void saveUsersToDataBase(AppUser userToInsert){
        try(Connection connection = connectionToDataBase()) {

            String sqlQuery = "INSERT INTO users(username, password, role) VALUES(?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, userToInsert.getUsername());
            pstmt.setString(2, userToInsert.getPassword());
            pstmt.setString(3, userToInsert.getRole().toString());
            pstmt.execute();

        } catch (SQLException | IOException ex) {
            String message = "Dogodila se greska kod spremanja korisnika u bazu podataka!";
            logger.error(message, ex);
            System.out.println(message);
        }
    }

    public static void saveAdsToDataBase(Ad adToInsert){

    }

    public static void saveCampaignToDataBase(Campaign campaignToInsert){
        try(Connection connection = connectionToDataBase()){
            String sqlQuery = "INSERT INTO campaign(name, description, status, startDate, endDate, budget, targetAudience, channels, createdBy, tvrtkaId) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, campaignToInsert.getName());
            pstmt.setString(2, campaignToInsert.getDescription());
            pstmt.setString(3, campaignToInsert.getStatus());
            pstmt.setString(4, campaignToInsert.getStartDate().toString());
            pstmt.setString(5, campaignToInsert.getEndDate().toString());
            pstmt.setBigDecimal(6, campaignToInsert.getBudget());
            pstmt.setString(7, campaignToInsert.getTargetAudience());
            pstmt.setString(8, campaignToInsert.getChannels());
            pstmt.setInt(9, campaignToInsert.getCreatedBy());
            pstmt.setInt(10, campaignToInsert.getCompanyId());
            pstmt.execute();


        }catch (SQLException | IOException ex) {
            String message = "Dogodila se greska kod spremanja kampanje u bazu podataka!";
            logger.error(message, ex);
            System.out.println(message);
        }
    }

    public static void saveCompanyToDataBase(Company companyToInsert){
        try(Connection connection = connectionToDataBase()){
            String sqlQuery = "INSERT INTO tvrtke(nazivTvrtke, adresaTvrtke, kontaktInformacije) VALUES(?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);

            pstmt.setString(1, companyToInsert.getCompanyName());
            pstmt.setString(2, companyToInsert.getCompanyAddress());
            pstmt.setString(3, companyToInsert.getCompanyContact());
            pstmt.execute();

        }catch (SQLException | IOException ex) {
            String message = "Dogodila se greska kod spremanja tvrtke u bazu podataka!";
            logger.error(message, ex);
            System.out.println(message);
        }
    }

    public static void saveAdToAdToDataBase(Ad insertToAd){
        try(Connection connection = connectionToDataBase()){
            String sqlQuery = "INSERT INTO ad(name, content, type, status, targetAudience, startDate, endDate, campaignID, impressions, clicks, conversions) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
            pstmt.setString(1, insertToAd.getName());
            pstmt.setString(2, insertToAd.getContent());
            pstmt.setString(3, insertToAd.getType());
            pstmt.setString(4, insertToAd.getStatus());
            pstmt.setString(5, insertToAd.getTargetAudience());
            pstmt.setDate(6, Date.valueOf(insertToAd.getStartDate().toString()));
            pstmt.setDate(7, Date.valueOf(insertToAd.getEndDate().toString()));
            pstmt.setInt(8, insertToAd.getCampaignId());
            pstmt.setInt(9, insertToAd.getImpressions());
            pstmt.setLong(10, insertToAd.getClicks());
            pstmt.setLong(11, insertToAd.getImpressions());
            pstmt.execute();

        }catch (SQLException | IOException ex) {
            String message = "Dogodila se greska kod spremanja reklama u bazu podataka!";
            logger.error(message, ex);
            System.out.println(message);
        }
    }







}
