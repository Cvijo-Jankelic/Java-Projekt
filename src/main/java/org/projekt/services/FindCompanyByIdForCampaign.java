package org.projekt.services;

import org.projekt.entity.Company;
import org.projekt.utils.DatabaseUtils;

import java.util.List;
import java.util.Optional;

public class FindCompanyByIdForCampaign {

    public static Optional<Company> findCompanyByCampaignId(Integer campaignId){
        List<Company> companies = DatabaseUtils.getCompaniesFromDataBase();

        return companies.stream()
                .filter(company -> company.getCompanyId().equals(campaignId))
                .findFirst();
    }

}
