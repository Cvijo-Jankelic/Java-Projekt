package org.projekt.builders;

import org.projekt.Enum.Status;
import org.projekt.entity.Campaign;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CampaignBuilder {
    private Integer campaignId;
    private String name;
    private String description;
    private Status status;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal budget;
    private String targetAudience;
    private String channels;
    private BigDecimal roi;
    private Integer createdBy;
    private Integer companyId;

    public CampaignBuilder setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    public CampaignBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CampaignBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public CampaignBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public CampaignBuilder setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public CampaignBuilder setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public CampaignBuilder setBudget(BigDecimal budget) {
        this.budget = budget;
        return this;
    }

    public CampaignBuilder setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
        return this;
    }

    public CampaignBuilder setChannels(String channels) {
        this.channels = channels;
        return this;
    }

    public CampaignBuilder setRoi(BigDecimal roi) {
        this.roi = roi;
        return this;
    }

    public CampaignBuilder setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public CampaignBuilder setCompanyId(Integer companyId) {
        this.companyId = companyId;
        return this;
    }

    public Campaign createCampaign() {
        return new Campaign(campaignId, name, description, status, startDate, endDate, budget, targetAudience, channels, roi, createdBy, companyId);
    }
}