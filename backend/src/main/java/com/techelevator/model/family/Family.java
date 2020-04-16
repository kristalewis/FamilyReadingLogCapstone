package com.techelevator.model.family;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Family {
    @NotBlank(message = "FamilyName is required")
    @NotNull(message = "FamilyName cannot be null")
    private String familyname;
    private long id;


    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}