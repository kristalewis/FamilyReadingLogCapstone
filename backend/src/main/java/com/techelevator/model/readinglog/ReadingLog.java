package com.techelevator.model.readinglog;

public class ReadingLog {

    private int logId;
    private int userId;
    private String username;
    private int bookId;
    private String title;
    private String readingFormat;
    private int minutesSpent;
    private String bookNotes;
    private boolean completedBook;

    public int getLogId() {
        return logId;
    }

    public boolean isCompletedBook() {
        return completedBook;
    }

    public void setCompletedBook(boolean completedBook) {
        this.completedBook = completedBook;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getReadingFormat() {
        return readingFormat;
    }

    public void setReadingFormat(String readingFormat) {
        this.readingFormat = readingFormat;
    }

    public int getMinutesSpent() {
        return minutesSpent;
    }

    public void setMinutesSpent(int minutesSpent) {
        this.minutesSpent = minutesSpent;
    }

    public String getBookNotes() {
        return bookNotes;
    }

    public void setBookNotes(String bookNotes) {
        this.bookNotes = bookNotes;
    }



}