package com.techelevator.model.prize;

import java.sql.Date;

public class Prize {

    private long prizeId;
    private int familyId;
    private String prizeName;
    private String prizeDescription;
    private int minutesNeeded;
    private String userRole;
    private int maxPrizes;
    private Date beginDate;
    private Date endDate;

    public long getPrizeId() {
        return prizeId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public int getMaxPrizes() {
        return maxPrizes;
    }

    public void setMaxPrizes(int maxPrizes) {
        this.maxPrizes = maxPrizes;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public int getMinutesNeeded() {
        return minutesNeeded;
    }

    public void setMinutesNeeded(int minutesNeeded) {
        this.minutesNeeded = minutesNeeded;
    }

    public String getPrizeDescription() {
        return prizeDescription;
    }

    public void setPrizeDescription(String prizeDescription) {
        this.prizeDescription = prizeDescription;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public void setPrizeId(long prizeId) {
        this.prizeId = prizeId;
    }
    
    

}