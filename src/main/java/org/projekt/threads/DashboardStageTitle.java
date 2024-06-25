package org.projekt.threads;

import org.projekt.entity.Campaign;
import org.projekt.exceptions.CampaignNotFoundException;
import org.projekt.runner.HelloApplication;
import org.projekt.services.LoginService;
import org.projekt.sort.Top5Campaigns;
import org.projekt.sort.TopBudgetSort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class DashboardStageTitle implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(DashboardStageTitle.class);

    @Override
    public void run() {

        try{
            Optional<Campaign> campaignOptional = TopBudgetSort.topCampaign();

            campaignOptional.ifPresent(campaign -> HelloApplication.getMainStage().setTitle("Najvrijednija kampanja " + campaign.getName()));
        }catch (CampaignNotFoundException ex){
            System.out.println(ex.getMessage());
            logger.error(ex.getMessage());
            ex.printStackTrace();
        }


    }
}
