package org.projekt.entity;

import org.projekt.Enum.Status;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Ad implements Serializable {
    private Integer adID;
    private String name;
    private String content;
    private String type;
    private Status status;
    private String targetAudience;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer campaignId;
    private Integer impressions;
    private Long clicks;
    private Integer conversions; // Uspjesno provedena akcija kao rezultat oglasavanja

    public Ad(Integer adID, String name, String content, String type, Status status,
              String targetAudience, LocalDateTime startDate, LocalDateTime endDate, Integer campaignId, Integer impressions,
              Long clicks, Integer conversions) {
        this.adID = adID;
        this.name = name;
        this.content = content;
        this.type = type;
        this.status = status;
        this.targetAudience = targetAudience;
        this.startDate = startDate;
        this.endDate = endDate;
        this.campaignId = campaignId;
        this.impressions = impressions;
        this.clicks = clicks;
        this.conversions = conversions;
    }

    public Integer getAdID() {
        return adID;
    }

    public void setAdID(Integer adID) {
        this.adID = adID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return  endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }

    public Integer getImpressions() {
        return impressions;
    }

    public void setImpressions(Integer impressions) {
        this.impressions = impressions;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Integer getConversions() {
        return conversions;
    }

    public void setConversions(Integer conversions) {
        this.conversions = conversions;
    }


    @Override
    public String toString() {
        return name;
    }
}
