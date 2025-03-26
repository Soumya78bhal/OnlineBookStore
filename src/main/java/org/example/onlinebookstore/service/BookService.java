package org.example.onlinebookstore.service;

import java.util.List;

import org.example.onlinebookstore.Entity.Book;
import org.example.onlinebookstore.exception.IdNotFoundException;
import org.example.onlinebookstore.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;  // Injecting BookRepository to interact with the database

    private static final Logger logger=LoggerFactory.getLogger(BookService.class);
    
    // Method to add a new book to the database
    public Book addBook(Book book) {
        bookRepository.save(book); // Saving the book entity
        logger.info("Book Added Successfully");
        return book;
    }

    // Method to retrieve all books from the database
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Method to fetch a book by its ID, throws exception if not found
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Book with id:" + id + " not Found"));
    }

    // Method to update an existing book's details
    public Book updateBook(Long id, Book details) {
        return bookRepository.findById(id).map(book -> {
            book.setAuthor(details.getAuthor()); // Updating the author
            book.setTitle(details.getTitle()); // Updating the title
            book.setPrice(details.getPrice()); // Updating the price
            book.setPublishedDate(details.getPublishedDate()); // Updating the published date
            return bookRepository.save(book); // Saving updated book
        }).orElseThrow(() -> new IdNotFoundException("Book with id:" + id + " not Found"));
    }

    // Method to delete a book by its ID, throws exception if not found
    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Book not Found")); // Throw exception if book not found
        bookRepository.delete(book); // Deleting the book
    }
}
