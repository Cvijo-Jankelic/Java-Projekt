package org.projekt.sort;

import org.projekt.entity.Campaign;
import org.projekt.exceptions.CampaignNotFoundException;
import org.projekt.utils.DatabaseUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TopBudgetSort {
    public static Optional<Campaign> topCampaign(){
        List<Campaign> campaigns = DatabaseUtils.getCampaignsFromDataBase();

        Optional<Campaign> topCampaign = topCampaign().stream()
                .max(Comparator.comparing(Campaign::getBudget));

        if(topCampaign().isEmpty()){
            throw new CampaignNotFoundException("Nije pronadjena najvrijednija kampanja");
        }


        return Optional.of(topCampaign.get());
    }
}
