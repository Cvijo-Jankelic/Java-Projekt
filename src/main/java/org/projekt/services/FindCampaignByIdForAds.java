package org.projekt.services;

import org.projekt.entity.Ad;
import org.projekt.entity.Campaign;
import org.projekt.utils.DatabaseUtils;

import java.util.List;
import java.util.Optional;

public class FindCampaignByIdForAds {

    public static Optional<Campaign> findCampaignByIdForAds(Integer campaignId){
        List<Campaign> campaignList = DatabaseUtils.getCampaignsFromDataBase();

        return campaignList.stream()
                .filter(campaign -> campaign.getCampaignId().equals(campaignId))
                .findFirst();
    }
}
