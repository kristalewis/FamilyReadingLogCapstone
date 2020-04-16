package com.techelevator.model.book;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcBookDao implements BookDao {
    
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcBookDao(DataSource d) {
        this.jdbcTemplate = new JdbcTemplate(d);
    }

    @Override
    public List<Book> getFamilyBookshelf(int familyId) {
        List<Book> bookshelf = new ArrayList<>();
        String sql = "SELECT book_id, family_id, isbn, title, author, image, description " +
                     "FROM books WHERE family_id = ? " +
                     "ORDER BY title;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, familyId);
        while (results.next()) {
            bookshelf.add(mapBook(results));
        }
        return bookshelf;
    }

    @Override
    public Book getBookById(long bookId) {
        Book book = new Book();
        String sql = "SELECT book_id, family_id, isbn, title, author, image, description " +
                    "FROM books " + 
                    "WHERE book_id = ?;";
        SqlRowSet results =  jdbcTemplate.queryForRowSet(sql, bookId);
        if(results.next()) {
            book = mapBook(results);
        }
        return book;
    }

    @Override
    public Book createBook(Book book) {

        long newBookId = jdbcTemplate.queryForObject("INSERT INTO books (family_id, isbn, title, author, image, description) " + 
                    "VALUES (?,?, ?, ?, ?, ?) RETURNING book_id;",
                    Long.class, book.getFamilyId(), book.getIsbn(), book.getTitle(),
                    book.getAuthor(), book.getImage(), book.getDescription());
        
        book.setBookId(newBookId);
        return book;
    }

    @Override
    public void deleteBook(long bookId) {

        String sql = "DELETE FROM books WHERE book_id = ?;";
        try {
            jdbcTemplate.update(sql, bookId);
        } catch (DataAccessException e) {
            System.out.println("Delete book failed" + "\n Book ID: " + bookId);
        }
    }

    @Override
    public boolean updateBook(Book book) {
        boolean isUpdated = false;

        String sql = "UPDATE books " +
                    "SET title = ?, author = ?, image = ?, description = ? " +
                    "WHERE book_id = ?";
        try {
            jdbcTemplate.update(sql, book.getTitle(),
            book.getAuthor(), book.getImage(), book.getDescription(), book.getBookId());
            isUpdated = true;
        } catch (DataAccessException e) {
            System.out.println("Update book failed" + "\n Book ID: " + book.getBookId());
        }
        return isUpdated;
    }

    public Book mapBook(SqlRowSet row) {
        Book b = new Book();
        b.setBookId(row.getLong("book_id"));
        b.setFamilyId(row.getInt("family_id"));
        b.setIsbn(row.getString("isbn"));
        b.setTitle(row.getString("title"));
        b.setImage(row.getString("image"));
        b.setDescription(row.getString("description"));
        b.setAuthor(row.getString("author"));
        return b;
    }

    @Override
    public List<Book> getPersonalBookshelf(int userId) {
        List<Book> bookshelf = new ArrayList<>();
        String sql = "SELECT DISTINCT books.book_id, books.family_id, isbn, title, author, image, description " +
                     "FROM currently_reading " +
                     "JOIN books ON currently_reading.book_id = books.book_id " +
                     "WHERE currently_reading.user_id = ? " +
                     "ORDER BY title;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            bookshelf.add(mapBook(results));
        }
        return bookshelf;
    }

    @Override
    public List<Book> getCompletedBooks(int userId) {
        List<Book> bookshelf = new ArrayList<>();
        String sql = "SELECT books.book_id, books.family_id, isbn, title, author, image, description " +
                     "FROM users " +
                     "JOIN reading_log ON users.id = reading_log.user_id " +
                     "JOIN books ON reading_log.book_id = books.book_id " +
                     "WHERE users.id = ? AND reading_log.completed_book = true " +
                     "ORDER BY title;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while (results.next()) {
            bookshelf.add(mapBook(results));
        }
        return bookshelf;
    }

    @Override
    public List<Book> getChildBookshelf(int familyId) {
        List<Book> bookshelf = new ArrayList<>();
        String sql = "SELECT DISTINCT books.book_id, books.family_id, isbn, title, author, image, description " +
                     "FROM books " +
                     "JOIN currently_reading ON books.book_id = currently_reading.book_id " +
                     "JOIN users ON currently_reading.user_id = users.id " +
                     "WHERE books.family_id = ? AND users.role = 'child' " +
                     "ORDER BY title;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, familyId);
        while (results.next()) {
            bookshelf.add(mapBook(results));
        }
        return bookshelf;
    }

    @Override
    public List<Book> getChildCompletedBooks(int familyId) {
        List<Book> bookshelf = new ArrayList<>();
        String sql = "SELECT books.book_id, books.family_id, isbn, title, author, image, description " +
                     "FROM users " +
                     "JOIN reading_log ON users.id = reading_log.user_id " +
                     "JOIN books ON reading_log.book_id = books.book_id " +
                     "WHERE users.family_id = ? AND reading_log.completed_book = true AND users.role = 'child' " +
                     "ORDER BY title;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, familyId);
        while (results.next()) {
            bookshelf.add(mapBook(results));
        }
        return bookshelf;
    }

    @Override
    public void addToPersonalBookshelf(int userId, int bookId) {
        String sql = "INSERT INTO currently_reading (user_id, book_id)" +
                     "VALUES (?, ?);";
        jdbcTemplate.update(sql, userId, bookId);
    }

    @Override
    public void removeFromPersonalBookshelf(int userId, int bookId) {
        String sql = "DELETE FROM currently_reading " +
                     "WHERE user_id = ? AND book_id = ?;";
        jdbcTemplate.update(sql, userId, bookId);
    }

    // @Override
    // public List<Book> getSingleChildCompletedBooks(int childId) {
    //     List<Book> bookshelf = new ArrayList<>();
    //     String sql = "SELECT DISTINCT books.book_id, books.family_id, isbn, title, author, image, description " +
    //                  "FROM users " +
    //                  "JOIN reading_log ON users.id = reading_log.user_id " +
    //                  "JOIN books ON reading_log.book_id = books.book_id " +
    //                  "WHERE users.id = ? AND reading_log.completed_book = true " +
    //                  "ORDER BY title;";
    //     SqlRowSet results = jdbcTemplate.queryForRowSet(sql, childId);
    //     while (results.next()) {
    //         bookshelf.add(mapBook(results));
    //     }
    //     return bookshelf;
    // }


    // @Override
    // public boolean createBook(Book book) {
    //     boolean isCreated = false;

    //     String sql = "INSERT INTO books (family_id, isbn, title, author, image, description) " + 
    //                 "VALUES (?, ?, ?, ?, ?, ?);";
    //     try {
    //         jdbcTemplate.update(sql, book.getFamilyId(), book.getIsbn(), book.getTitle(),
    //                             book.getAuthor(), book.getImage(), book.getDescription());
    //         isCreated = true;
    //     } catch (DataAccessException e) {
    //         System.out.println("Create book failed" + "\n ISBN: " + book.getIsbn());
    //     }
    //     return isCreated;
    // }

}