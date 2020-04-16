package com.techelevator.model.prize;

import java.sql.Date;

public class Award {
    private long prizeId;
    private int familyId;
    private int userId;
    private String userName;
    private String prizeName;
    private String prizeDescription;
    private int minutesNeeded;
    private Date awardedDate;

    public long getPrizeId() {
        return prizeId;
    }

    public Date getAwardedDate() {
        return awardedDate;
    }

    public void setAwardedDate(Date awardDate) {
        this.awardedDate = awardDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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