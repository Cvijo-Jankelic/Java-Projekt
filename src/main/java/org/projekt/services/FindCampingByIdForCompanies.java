package org.projekt.services;

import org.projekt.entity.Campaign;
import org.projekt.utils.DatabaseUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FindCampingByIdForCompanies {
    public static Optional<List<Campaign>> findCampaignsByCompanyId(Integer companyId) {
        List<Campaign> campaignList = DatabaseUtils.getCampaignsFromDataBase();

        // Filtriraj kampanje prema companyId
        List<Campaign> filteredCampaigns = campaignList.stream()
                .filter(campaign -> campaign.getCompanyId().equals(companyId))
                .collect(Collectors.toList());

        // Vraća Optional koji može biti prazan ako nema kampanja za tvrtku
        return filteredCampaigns.isEmpty() ? Optional.empty() : Optional.of(filteredCampaigns);
    }
}
