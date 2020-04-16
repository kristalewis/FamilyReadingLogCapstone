package com.techelevator.model.book;

import javax.validation.constraints.NotBlank;

public class Book {

    private long bookId;
    private int familyId;

    @NotBlank(message="ISBN is required")
    private String isbn;

    @NotBlank(message = "A book title is required")
    private String title;

    @NotBlank(message = "An author name is required")
    private String author;

    @NotBlank(message = "Image is required")
    private String image;

    private String description;

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }
    
    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return isbn + " " + title + " " + author + " " + image + " " + description;
    }

    // @Override
    // public boolean equals() {

    // }
}