package com.techelevator.model;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Date;

public class Space {
    private Long spaceId;
    private String name;
    private Boolean isAccessible;
    private String openDate;
    private String closeDate;
    private BigDecimal dailyRate;
    private Long maxOccupancy;


    public Long getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Long spaceId) {
        this.spaceId = spaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isAccessible() {
        return isAccessible;
    }

    public void setAccessible(Boolean accessible) {
        isAccessible = accessible;
    }

    public String getOpenDate() {
        return openDate;
    }

    public void setOpenDate(String openDate) {
        this.openDate = openDate;
    }

    public String getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(String closeDate) {
        this.closeDate = closeDate;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }

    public long getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(Long maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    public String toString(){
        return name;
    }


}