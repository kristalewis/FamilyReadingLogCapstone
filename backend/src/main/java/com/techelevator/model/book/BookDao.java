package com.techelevator.model.book;

import java.util.List;

public interface BookDao {

    public List<Book> getFamilyBookshelf(int familyId);

    public List<Book> getPersonalBookshelf(int userId);

    public List<Book> getCompletedBooks(int userId);

    public List<Book> getChildBookshelf(int familyId);

    public List<Book> getChildCompletedBooks(int familyId);

    public void addToPersonalBookshelf(int userId, int bookId);

    public void removeFromPersonalBookshelf(int userId, int bookId);

    public Book getBookById(long bookId);

    public Book createBook(Book book);

    public void deleteBook(long bookId);

    public boolean updateBook(Book book);
}