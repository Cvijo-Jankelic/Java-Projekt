package org.projekt.entity;

import java.util.List;

public class Company {
    private Integer companyId;
    private String companyName;
    private String companyAddress;
    private String companyContact;
    private List<Campaign> campaignList;

    public Company(Integer companyId, String companyName, String companyAddress, String companyContact) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyContact = companyContact;
    }

    public Company(Integer companyId, String companyName, String companyAddress, String companyContact, List<Campaign> campaignList) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.companyContact = companyContact;
        this.campaignList = campaignList;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public List<Campaign> getCampaignList() {
        return campaignList;
    }

    public void setCampaignList(List<Campaign> campaignList) {
        this.campaignList = campaignList;
    }


    @Override
    public String toString() {
        return companyName;
    }
}
