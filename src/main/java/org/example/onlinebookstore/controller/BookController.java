package org.example.onlinebookstore.controller;

import java.util.List;

import org.example.onlinebookstore.Entity.Book;
import org.example.onlinebookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books") // Base path for all endpoints in this controller
public class BookController {

    @Autowired
    private BookService bookService; // Injecting the BookService to handle business logic

    // Endpoint to create a new book
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        bookService.addBook(book); // Calling service method to add book
        return ResponseEntity.ok(book); // Returning the created book
    }

    // Endpoint to retrieve all books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // Endpoint to get a book by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id); // Fetching the book by ID
        return ResponseEntity.ok(book); // Returning the book details
    }

    //Duplicate method for git testing function
    @GetMapping("/find/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id) {

        Book book = bookService.getBookById(id); // Fetching the book by ID
        return ResponseEntity.ok(book); // Returning the book details
    }

    // Endpoint to update an existing book
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Book updatedBook = bookService.updateBook(id, bookDetails); // Updating the book
        return ResponseEntity.ok(updatedBook); // Returning the updated book

    }

    // Endpoint to delete a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id); // Deleting the book
        return ResponseEntity.ok("Book deleted successfully."); // Returning success message
    }
}
