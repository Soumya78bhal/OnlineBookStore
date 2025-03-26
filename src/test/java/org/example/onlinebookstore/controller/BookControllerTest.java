package org.example.onlinebookstore.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.example.onlinebookstore.Entity.Book;
import org.example.onlinebookstore.service.BookService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class BookControllerTest {

    @Mock
    private BookService bookService; 

    @InjectMocks
    private BookController bookController; 
    
    List<Book> books = new ArrayList<>(); // List to store sample book data
    private Book sampleBook; // Sample book instance

    @BeforeEach
    void setDummyData() {
        MockitoAnnotations.openMocks(this); // Initialize mocks before each test
        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setAuthor("Author Name");
        sampleBook.setTitle("Sample Book");
        sampleBook.setPrice(BigDecimal.valueOf(19.99));
        sampleBook.setPublishedDate(LocalDate.of(2022, 1, 1));

        books.add(sampleBook); // Adding sample book to list
    }

    @Test
    void testCreateBook() {
        // Mocking the addBook method of bookService to return sampleBook
        when(bookService.addBook(any(Book.class))).thenReturn(sampleBook);
        
        ResponseEntity<Book> response = bookController.createBook(sampleBook);

        // Asserting that the response contains the correct book
        assertEquals(sampleBook, response.getBody());
    }

    @Test
    void testDeleteBook() {
        // Mocking deleteBookById method to do nothing
        doNothing().when(bookService).deleteBookById(1L);
        
        ResponseEntity<String> response = bookController.deleteBook(1L);
        
        // Asserting that the response message is correct
        assertEquals("Book deleted successfully.", response.getBody());
        
        // Verifying that the service method was called once
        verify(bookService, times(1)).deleteBookById(1L);
    }

    @Test
    void testGetAllBooks() {
        // Mocking getAllBooks method to return list of books
        when(bookService.getAllBooks()).thenReturn(books);

        List<Book> response = bookController.getAllBooks();
        
        // Asserting that the list size is as expected
        assertEquals(1, response.size());
        
        // Verifying that the service method was called once
        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookById() {
        // Mocking getBookById method to return sampleBook
        when(bookService.getBookById(1L)).thenReturn(sampleBook);
        
        ResponseEntity<Book> response = bookController.getBookById(1L);
        
        // Asserting that the correct book is returned
        assertEquals(sampleBook, response.getBody());
        
        // Verifying that the service method was called once
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void testUpdateBook() {
        // Mocking updateBook method to return sampleBook
        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(sampleBook);
        
        ResponseEntity<Book> response = bookController.updateBook(1L, sampleBook);
        
        // Asserting that the updated book is returned
        assertEquals(sampleBook, response.getBody());
        
        // Verifying that the service method was called once
        verify(bookService, times(1)).updateBook(1L, sampleBook);
    }
}
