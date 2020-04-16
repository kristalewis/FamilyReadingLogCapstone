package com.techelevator.model.family;

import java.util.List;

public interface FamilyDao {

    /**
     * Save a new family to the database. 
     *
     * @param familyName the name to give the Family
     * @return the new Family
     */
    public Family saveFamily(String familyName);
     
    /**
     * Get all of the Families from the database.
     * @return a List of family objects
     */
    public List<Family> getAllfamilies();

     /**
     *  gets the name of family provided and ID 
     *
     * @param familyID the ID of the family 
     * @return the Family found
     */
    public Family getFamilyById(int familyID);

    /**
     *  Deletes the family provided an ID
     *
     * @param familyID the ID of the family 
     * @return void
     */

    public void deleteFamilyById(int familyID);
}