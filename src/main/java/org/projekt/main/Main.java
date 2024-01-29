package org.projekt.main;

import org.projekt.entity.AppUser;
import org.projekt.entity.Campaign;
import org.projekt.entity.Company;
import org.projekt.utils.DatabaseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        logger.info("Program je pokrenut");

        List<Campaign> campaignList = new ArrayList<>();
        List<AppUser> appUsers = new ArrayList<>();
        List<Company> companies = new ArrayList<>();

        campaignList = DatabaseUtils.getCampaignsFromDataBase();
        appUsers = DatabaseUtils.getAppUsersFromDataBase();
        companies = DatabaseUtils.getCompaniesFromDataBase();


    }
}
