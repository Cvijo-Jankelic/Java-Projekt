package org.projekt.entity;

import java.util.Date;

public class Ad {
    private Integer adID;
    private String name;
    private String content;
    private String type;
    private String status;
    private String targetAudience;
    private Date startDate;
    private Date endDate;
    private Integer campaignId;
    private Integer impressions;
    private Integer clicks;
    private Integer conversions;

    public Ad(Integer adID, String name, String content, String type, String status,
              String targetAudience, Date startDate, Date endDate, Integer campaignId, Integer impressions,
              Integer clicks, Integer conversions) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
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

    public Integer getClicks() {
        return clicks;
    }

    public void setClicks(Integer clicks) {
        this.clicks = clicks;
    }

    public Integer getConversions() {
        return conversions;
    }

    public void setConversions(Integer conversions) {
        this.conversions = conversions;
    }
}
