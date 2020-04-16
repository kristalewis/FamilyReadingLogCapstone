package com.techelevator.model.prize;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Component
public class JDBCPrizeDao implements PrizeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public  JDBCPrizeDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }




    @Override
    public Prize createPrize(Prize prize) {

        String sql = "INSERT INTO prizes(family_id, prize_name, prize_description, minutes_needed, " +
               "user_role, max_prizes, begin_date, end_date) VALUES (?,?,?,?,?,?,?,?) RETURNING prize_id;";

        long newId = jdbcTemplate.queryForObject(
                sql, Long.class,
                prize.getFamilyId(),prize.getPrizeName(),prize.getPrizeDescription(),prize.getMinutesNeeded(),
                prize.getUserRole(),prize.getMaxPrizes(), prize.getBeginDate(),prize.getEndDate());

        prize.setPrizeId(newId);

        return prize;
    }

    @Override
    public Prize getPrize(long prizeId) {
        Prize prize = new Prize();

        String sql = "SELECT prize_id, family_id, prize_name, prize_description, minutes_needed," +
                      " user_role, max_prizes, begin_date, end_date " +
                      " FROM prizes " +
                      " WHERE prize_id = ?;";
        
        SqlRowSet results =  jdbcTemplate.queryForRowSet(sql, prizeId);
        if(results.next()) {
            prize = mapPrize(results);
        }
        return prize;

    }

    @Override
    public List<Prize> getAllPrizesByFamilyId(int familyID) {
        List<Prize> prizes = new ArrayList<Prize>();
        String sql = "SELECT prize_id, family_id, prize_name, prize_description, minutes_needed, " +
                      "user_role, max_prizes, begin_date, end_date " +
                      "FROM prizes " +
                      "WHERE family_id = ? AND prizes.max_prizes > 0 " +
                      "ORDER BY prize_name;";
        
        SqlRowSet results =  jdbcTemplate.queryForRowSet(sql, familyID);
        while (results.next()) {
            prizes.add(mapPrize(results));
        }
        return prizes;
    }

    public Prize mapPrize(SqlRowSet row) {
        Prize p = new Prize();
        p.setPrizeId(row.getLong("prize_id"));
        p.setFamilyId(row.getInt("family_id"));
        p.setPrizeName(row.getString("prize_name"));
        p.setPrizeDescription(row.getString("prize_description"));
        p.setMinutesNeeded(row.getInt("minutes_needed"));
        p.setUserRole(row.getString("user_role"));
        p.setMaxPrizes(row.getInt("max_prizes"));
        p.setBeginDate(row.getDate("begin_date"));
        p.setEndDate(row.getDate("end_date"));
        return p;
    }


    @Override
    public boolean updatePrize(Prize prize) {
        
        boolean isUpdated = false;

        String sql = "UPDATE prizes " +
                     "SET family_id = ?, prize_name = ?, prize_description = ?, minutes_needed = ?, " +
                     "user_role = ?, max_prizes = ?, begin_date = ?, end_date = ? " +
                     "WHERE prize_id = ?";
        try {
            jdbcTemplate.update(sql, prize.getFamilyId(), prize.getPrizeName(), prize.getPrizeDescription(), 
            prize.getMinutesNeeded(), prize.getUserRole(), prize.getMaxPrizes(), prize.getBeginDate(), prize.getEndDate(),
            prize.getPrizeId());
            isUpdated = true;
        } catch (DataAccessException e) {
            System.out.println("Update prize failed" + "\n prize ID: " + prize.getPrizeId());
        }
        return isUpdated;
    }

    @Override
    public void deletePrizeById(int prizeId) {
        String sql = "DELETE FROM prizes WHERE prize_id = ?;";
        try {
            jdbcTemplate.update(sql, prizeId);
        } catch (DataAccessException e) {
            System.out.println("Delete prize failed" + "\n prize ID: " + prizeId);
        }

    }
    //M.Shell 4/15/2020 added to support prize_user inserts.
    @Override
    public long createPrizeUser(int prizeId, int userId) {
        String sql = "INSERT INTO prizes_users(prize_id, user_id) "
                     + "VALUES(?,?) "
                     + "RETURNING prize_user_id;";
      
        long newId = jdbcTemplate.queryForObject(
            sql, Long.class,
            prizeId, userId);
        return newId;
    }

    @Override
    public List<Prize> getPrizesByUserId(int userID) {
        List<Prize> prizes = new ArrayList<Prize>();
        String sql = "SELECT prizes.prize_id, family_id, "
                    + "prize_name, prize_description, minutes_needed, " 
                    + "user_role, max_prizes, begin_date, end_date "
                    + "FROM prizes "
                    + "JOIN prizes_users ON prizes.prize_id = prizes_users.prize_id "
                    + "WHERE prizes_users.user_id = ?;";
        SqlRowSet results =  jdbcTemplate.queryForRowSet(sql, userID);
                    while (results.next()) {
                        prizes.add(mapPrize(results));
                    }
        return prizes;
    }
    
    @Override
    public void removePrizeById(int prizeId) {
        String sql = "UPDATE prizes SET max_prizes = 0 " +
                     "WHERE prize_id = ?;";
        jdbcTemplate.update(sql, prizeId);
    }

     //S.Stier 4/16/2020 added awarded Date
    @Override
    public List<Award> getPrizesByFamilyId(int familyId) {
        List<Award> awards = new ArrayList<Award>();
        String sql = "SELECT prizes.prize_id, prizes.family_id, id, users.username, "
                    + "prize_name, prize_description, minutes_needed, prizes_users.awarded_date, " 
                    + "user_role, max_prizes, begin_date, end_date "
                    + "FROM prizes "
                    + "JOIN prizes_users ON prizes.prize_id = prizes_users.prize_id "
                    + "JOIN users ON prizes_users.user_id = users.id "
                    + "WHERE users.family_id = ?;";
        SqlRowSet results =  jdbcTemplate.queryForRowSet(sql, familyId);
                    while (results.next()) {
                        awards.add(mapAward(results));
                    }
        return awards;
    }

    @Override
    public void updateClaimedPrize(int prizeId) {
        String sql = "UPDATE prizes SET max_prizes = (max_prizes - 1) " +
                     "WHERE prize_id = ?;";
        jdbcTemplate.update(sql, prizeId);
    }

   
    //S.Stier 4/16/2020 added awarded Date
    public Award mapAward(SqlRowSet row) {
        Award a = new Award();
        a.setPrizeId(row.getLong("prize_id"));
        a.setFamilyId(row.getInt("family_id"));
        a.setUserName(row.getString("username"));
        a.setPrizeName(row.getString("prize_name"));
        a.setPrizeDescription(row.getString("prize_description"));
        a.setMinutesNeeded(row.getInt("minutes_needed"));
        a.setUserId(row.getInt("id"));
        a.setAwardedDate(row.getDate("awarded_date"));
        return a;
    }

}