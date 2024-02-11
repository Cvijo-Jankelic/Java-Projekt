package org.projekt.builders;

import org.projekt.entity.Campaign;
import org.projekt.entity.Company;

import java.util.List;

public class CompanyBuilder {
    private Integer companyId;
    private String companyName;
    private String companyAddress;
    private String companyContact;
    private List<Campaign> campaignList;

    public CompanyBuilder setCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public CompanyBuilder setCompanyCampaign(List<Campaign> campaignList){
        this.campaignList = campaignList;
        return this;
    }

    public CompanyBuilder setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public CompanyBuilder setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
        return this;
    }

    public CompanyBuilder setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
        return this;
    }

    public Company createCompany() {
        return new Company(companyId, companyName, companyAddress, companyContact, campaignList);
    }
}