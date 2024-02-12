package org.projekt.builders;

import org.projekt.Enum.Status;
import org.projekt.entity.Ad;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class AdBuilder {
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
    private Integer conversions;

    public AdBuilder setAdID(Integer adID) {
        this.adID = adID;
        return this;
    }

    public AdBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public AdBuilder setContent(String content) {
        this.content = content;
        return this;
    }

    public AdBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public AdBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public AdBuilder setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
        return this;
    }

    public AdBuilder setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public AdBuilder setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public AdBuilder setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    public AdBuilder setImpressions(Integer impressions) {
        this.impressions = impressions;
        return this;
    }

    public AdBuilder setClicks(Long clicks) {
        this.clicks = clicks;
        return this;
    }

    public AdBuilder setConversions(Integer conversions) {
        this.conversions = conversions;
        return this;
    }

    public Ad createAd() {
        return new Ad(adID, name, content, type, status, targetAudience, startDate, endDate, campaignId, impressions, clicks, conversions);
    }
}