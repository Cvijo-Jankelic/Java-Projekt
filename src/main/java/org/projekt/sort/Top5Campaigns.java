package org.projekt.sort;

import org.projekt.entity.Campaign;
import org.projekt.utils.DatabaseUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Top5Campaigns {
    public static List<Campaign> getTop5Campaigns(){
        List<Campaign> campaigns = DatabaseUtils.getCampaignsFromDataBase();

        List<Campaign> topCampaigns = campaigns.stream()
                .sorted((c1, c2) -> c2.getBudget().compareTo(c1.getBudget())) // Sortiranje po budgetu, od najvećeg prema najmanjem
                .limit(5) // Uzima top 5
                .collect(Collectors.toList());

        return topCampaigns;
    }

}
