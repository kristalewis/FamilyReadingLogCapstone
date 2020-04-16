package com.techelevator.model.readinglog;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.techelevator.model.login.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcReadingLogDao implements ReadingLogDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcReadingLogDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<ReadingLog> getChildLogs(int familyId) {
        List<ReadingLog> allLogs = new ArrayList<ReadingLog>();
        String sql = "SELECT reading_log.log_id, reading_log.user_id, users.username, " + 
                            "books.book_id, books.title, reading_log.reading_format, " +
                            "reading_log.time_spent, reading_log.notes, reading_log.completed_book " +
                     "FROM families " +
                     "JOIN users ON families.family_id = users.family_id " +
                     "JOIN reading_log ON users.id = reading_log.user_id " +
                     "JOIN books ON reading_log.book_id = books.book_id " +
                     "WHERE families.family_id = ? AND users.role = 'child' " +
                     "ORDER BY reading_log.log_id DESC " +
                     "LIMIT 25;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, familyId);
        while(results.next()) {
            allLogs.add(logFromRowSet(results));
        }
        return allLogs;
    }

    @Override
    public List<ReadingLog> getAllLogsForUser(int userId) {
        List<ReadingLog> allLogs = new ArrayList<ReadingLog>();
        String sql = "SELECT reading_log.log_id, reading_log.user_id, users.username, books.book_id, books.title, reading_log.reading_format, " +
                    "reading_log.time_spent, reading_log.notes, reading_log.completed_book " +
                    "FROM users " +
                    "JOIN reading_log ON users.id = reading_log.user_id " +
                    "JOIN books ON reading_log.book_id = books.book_id " +
                    "WHERE users.id = ? " +
                    "ORDER BY reading_log.log_id DESC " +
                    "LIMIT 25;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while(results.next()) {
            allLogs.add(logFromRowSet(results));
        }
        return allLogs;
    }

    @Override
    public void createLog (ReadingLog log) {
        String createSql = "INSERT INTO reading_log (user_id, book_id, reading_format, " + 
                                "time_spent, notes, completed_book) " +
                           "VALUES (?, ?, ?, ?, ?, ?);";
        jdbcTemplate.update(createSql, log.getUserId(), log.getBookId(), log.getReadingFormat(), 
                            log.getMinutesSpent(), log.getBookNotes(), log.isCompletedBook());
    }

    @Override
    public void deleteLog (int logId) {
        String sql = "DELETE FROM reading_log WHERE logId = ?;";
        try {
            jdbcTemplate.update(sql, logId);
        } catch (DataAccessException e) {
            System.out.println("Failed to delete log.");
        }
    }



    private ReadingLog logFromRowSet(SqlRowSet row) {
        ReadingLog r = new ReadingLog();
        r.setLogId(row.getInt("log_id"));
        r.setUserId(row.getInt("user_id"));
        r.setUsername(row.getString("username"));
        r.setBookId(row.getInt("book_id"));
        r.setTitle(row.getString("title"));
        r.setReadingFormat(row.getString("reading_format"));
        r.setMinutesSpent(row.getInt("time_spent"));
        r.setBookNotes(row.getString("notes"));
        r.setCompletedBook(row.getBoolean("completed_book"));
        return r;
    }

}