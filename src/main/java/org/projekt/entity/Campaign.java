package org.projekt.entity;

import org.projekt.Enum.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class Campaign {
    private Integer campaignId;
    private String name;
    private String description;
    private Status status; // indikator statusa kampanje off/on/pause
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal budget;
    private String targetAudience; // ciljana publika
    private String channels; // kanali na kojima se kampanja provodi(drustveni mediji, web, mail, itd..)
    private BigDecimal roi; // povrat ulaganja, moze biti izracunat ili azuriran nakon provedbe kampanje. (Ovo cemo jos razmislit ocemo li ostaviti u projektu ili ne)
    private Integer createdBy; // Id korisnika
    private Integer companyId;

    public Campaign(Integer campaignId, String name, String description, Status status,
                    LocalDate startDate, LocalDate endDate, BigDecimal budget, String targetAudience, String channels,
                    BigDecimal roi, Integer createdBy, Integer companyId) {

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
        this.companyId = companyId;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return name;
    }
}

