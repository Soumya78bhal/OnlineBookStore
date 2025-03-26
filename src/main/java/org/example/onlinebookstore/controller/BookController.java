package org.example.onlinebookstore.controller;

import org.example.onlinebookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.onlinebookstore.Entity.Book;

import java.util.List;

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
    @@GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
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
