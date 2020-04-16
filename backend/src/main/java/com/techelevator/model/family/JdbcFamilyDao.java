package com.techelevator.model.family;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Component
public class JdbcFamilyDao implements FamilyDao {

    private JdbcTemplate jdbcTemplate;

    /**
     * Create a new family dao with the supplied data source 
     *
     * @param dataSource an SQL data source
     */
    @Autowired
    public  JdbcFamilyDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Save a new family to the database. 
     *
     * @param familyName the name of the family
     */
    @Override
    public Family saveFamily(String familyName) {

        long newId = jdbcTemplate.queryForObject(
                "INSERT INTO families(name) VALUES (?) RETURNING family_id", Long.class,
                familyName);

        Family newFamily = new Family();
        newFamily.setId(newId);
        newFamily.setFamilyname(familyName);

        return newFamily;
    }

    @Override
    public List<Family> getAllfamilies() {
        List<Family> families = new ArrayList<Family>();
        String sql = "SELECT family_id, name FROM families";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);

        while (results.next()) {
            Family family = mapResultToFamily(results);
            families.add(family);
        }

        return families;
    }

    private Family mapResultToFamily(SqlRowSet results) {
        Family family = new Family();
        family.setId(results.getLong("family_id"));
        family.setFamilyname(results.getString("name"));
        return family;
    }

    @Override
    public Family getFamilyById(int familyID) {
        String sql = "SELECT family_id, name FROM families where family_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, familyID);

        if (results.next()) {
            return mapResultToFamily(results);
        } else {
            return null;
        }
    }

    @Override
    public void deleteFamilyById(int familyID) {
        String sql = "DELETE FROM families WHERE family_id = ?;";
        try {
            jdbcTemplate.update(sql, familyID);
        } catch (DataAccessException e) {
            System.out.println("Delete family failed" + "\n family_id: " + familyID);
        }

    }
}