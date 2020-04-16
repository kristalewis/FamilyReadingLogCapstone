package com.techelevator.model.prize;

import java.util.List;

public interface PrizeDao {
    
    /**
     * Save a new Prize to the database. 
     *
     * @param Prize Object
     * @return the new created Prize
     */
    public Prize createPrize(Prize prize);

    /**
     * get a prize from the database 
     * 
     *
     * @param pass it the prize ID
     * @return the prize Found
     */
    public Prize getPrize(long prizeId);
    
    /**
     * Update a prize in the database. 
     *
     * @param Prize Object
     * @return boolean
     */
    public boolean updatePrize(Prize prize);

     /**
     *  Deletes the Prize provided an ID
     *
     * @param familyID the ID of the family 
     * @return void
     */
    public void deletePrizeById(int prizeId);
    
     /**
     *  get all the prizes for a given family
     *
     * @param familyID the ID of the family 
     * @return the prizes for That family
     */
    public List<Prize> getAllPrizesByFamilyId(int familyID);

    public long createPrizeUser(int prizeId, int userId);

    public List<Prize> getPrizesByUserId(int userID);

    public List<Award> getPrizesByFamilyId(int familyId);
    
    public void removePrizeById(int prizeId);

    public void updateClaimedPrize(int prizeId);

    
}