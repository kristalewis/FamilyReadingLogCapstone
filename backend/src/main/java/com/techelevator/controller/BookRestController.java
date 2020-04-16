package com.techelevator.controller;

import java.util.List;

import javax.validation.Valid;

import com.techelevator.authentication.AuthProvider;
import com.techelevator.authentication.UnauthorizedException;
import com.techelevator.model.book.Book;
import com.techelevator.model.book.BookDao;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin
@RequestMapping("/api/books")
public class BookRestController {

    private AuthProvider authProvider;
    private BookDao bookDao;
    
    @Autowired
    public BookRestController(AuthProvider authProvider, BookDao bookDao){
        this.authProvider = authProvider;
        this.bookDao = bookDao; 
        }

    // Get all family bookshelf
    @GetMapping("/{familyId}")
    public List<Book> getAllBooks(@PathVariable int familyId) throws UnauthorizedException {
        //if (!authProvider.userHasRole(new String[] { "admin", "parent","child" })) {
        //    throw new UnauthorizedException();
        //}
        if (familyId <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid family ID.");
        }
        return bookDao.getFamilyBookshelf(familyId);
    }

    // create a new book
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Book CreateBook(@Valid @RequestBody Book newBook, BindingResult result) {
        if(result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing field");
        }
        try {
            return bookDao.createBook(newBook);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid family ID.");
        }
    }

    //get book by book Id
    @GetMapping("/id/{bookId}") 
    public Book getBookById(@PathVariable(required = true) long bookId) {
        Book existingBook = bookDao.getBookById(bookId);

        if (existingBook.getBookId() <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not a valid book ID");
        }
        return bookDao.getBookById(bookId);
    }
    
    // This updates the book
    @PutMapping("/{bookId}") 
    public boolean updateBookById(@PathVariable(required = true) long bookId, @RequestBody Book updatedBook) {
        
        Book existingBook = bookDao.getBookById(bookId);
        if (existingBook.getBookId() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book Id does not exist");
        }
        return bookDao.updateBook(updatedBook);
        
    }
    // This deletes the book by book id
    @DeleteMapping("/{bookId}") 
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable long bookId) {
        Book existingBook = bookDao.getBookById(bookId);

        if (existingBook.getBookId() == 0){
            System.out.println("Book ID does not exist");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book does not exist");
        }
        bookDao.deleteBook(bookId);
    }

    // Get all personal bookshelf
    @GetMapping("/personalbookshelf/{userId}")
    public List<Book> getPersonalBookshelf(@PathVariable int userId) throws UnauthorizedException {
        return bookDao.getPersonalBookshelf(userId);
    }

    //Add book to personalbookshelf
    @PostMapping("/personalbookshelf/{userId}/{bookId}")
    public void addToPersonalBookshelf(@PathVariable int userId, @PathVariable int bookId) throws UnauthorizedException {
         bookDao.addToPersonalBookshelf(userId, bookId);
    }

    //Remove book from personalbookshelf
    @DeleteMapping("/personalbookshelf/{userId}/{bookId}")
    public void removeFromPersonalBookshelf(@PathVariable int userId, @PathVariable int bookId) throws UnauthorizedException {
         bookDao.removeFromPersonalBookshelf(userId, bookId);
    }

     // Get completed books
     @GetMapping("/completed/{userId}")
     public List<Book> getCompletedBooks(@PathVariable int userId) throws UnauthorizedException {
         return bookDao.getCompletedBooks(userId);
     }

    // Get all children bookshelf
    @GetMapping("/childbookshelf/{familyId}")
    public List<Book> getChildBookshelf(@PathVariable int familyId) throws UnauthorizedException {
        return bookDao.getChildBookshelf(familyId);
    }

    // Get books all children have completed
    @GetMapping("/completed/child/{familyId}")
    public List<Book> getChildCompletedBooks(@PathVariable int familyId) throws UnauthorizedException {
        return bookDao.getChildCompletedBooks(familyId);
    }



    
    
}