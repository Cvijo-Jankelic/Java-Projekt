package org.projekt.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Campaign {
    private Integer campaignId;
    private String name;
    private String description;
    private String status; // indikator statusa kampanje off/on/pause
    private Date startDate;
    private Date endDate;
    private BigDecimal budget;
    private String targetAudience; // ciljana publika
    private String channels; // kanali na kojima se kampanja provodi(drustveni mediji, web, mail, itd..)
    private BigDecimal roi; // povrat ulaganja, moze biti izracunat ili azuriran nakon provedbe kampanje. (Ovo cemo jos razmislit ocemo li ostaviti u projektu ili ne)
    private Integer createdBy; // Id korisnika

    public Campaign(Integer campaignId, String name, String description, String status,
                    Date startDate, Date endDate, BigDecimal budget, String targetAudience, String channels,
                    BigDecimal roi, Integer createdBy) {

        this.campaignId = campaignId;
        this.name = name;
        this.description = description;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.targetAudience = targetAudience;
        this.channels = channels;
        this.budget = budget;
        this.roi = roi;
        this.createdBy = createdBy;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public String getChannels() {
        return channels;
    }

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getRoi() {
        return roi;
    }

    public void setRoi(BigDecimal roi) {
        this.roi = roi;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
}

