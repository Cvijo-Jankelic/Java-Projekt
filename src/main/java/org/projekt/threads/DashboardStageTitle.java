package org.projekt.threads;

import org.projekt.entity.Campaign;
import org.projekt.runner.HelloApplication;
import org.projekt.sort.Top5Campaigns;
import org.projekt.sort.TopBudgetSort;

import java.util.Optional;

public class DashboardStageTitle implements Runnable{
    @Override
    public void run() {
        Optional<Campaign> campaignOptional = TopBudgetSort.topCampaign();

        campaignOptional.ifPresent(campaign -> HelloApplication.getMainStage().setTitle("Najvrijednija kampanja " + campaign.getName()));
    }
}
