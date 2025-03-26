package org.example.onlinebookstore.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.onlinebookstore.Entity.Book;
import org.example.onlinebookstore.exception.IdNotFoundException;
import org.example.onlinebookstore.repository.BookRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class BookServiceTest {
    @Mock // Mocking the BookRepository to avoid actual database interactions
    private BookRepository bookRepository;
    
    @InjectMocks // Injecting mocks into BookService
    private BookService bookService;
    private Book sampleBook;
    private List<Book> book = new ArrayList<>();
    
    @BeforeEach
    private void setDummyData() {
        MockitoAnnotations.openMocks(this); // Initializes mocks before each test
        
        // Creating a sample book object for testing
        Book book1 = new Book();
        book1.setId(1L);
        book1.setAuthor("ME");
        book1.setPrice(BigDecimal.valueOf(19.54));
        book1.setPublishedDate(LocalDate.of(2002, 12, 10));
        book1.setTitle("Death of bowling");
        
        sampleBook = book1;
        book.add(book1);
    }
 
    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(book); // Mocking findAll()
        List<Book> result = bookService.getAllBooks();
        assertEquals(result.get(0).getAuthor(), "ME"); // Verifying the retrieved book
    }

    @Test
    void testAddBook() {
        when(bookRepository.save(sampleBook)).thenReturn(sampleBook); // Mocking save()
        Book addedBook = bookService.addBook(sampleBook);

        assertEquals(addedBook.getAuthor(), "ME"); // Verifying saved book
        verify(bookRepository, times(1)).save(sampleBook); // Ensuring save() is called once
    }

    @Test
    void testDeleteBookById() {
        when(bookRepository.findById(sampleBook.getId())).thenReturn(Optional.of(sampleBook)); // Mock findById()
        doNothing().when(bookRepository).delete(sampleBook); // Mock delete()
        
        bookService.deleteBookById(sampleBook.getId()); // Execute delete operation
        
        verify(bookRepository, times(1)).findById(sampleBook.getId()); // Check if findById() was called
        verify(bookRepository, times(1)).delete(sampleBook); // Verify delete() was called
    }
   
    @Test
    void testGetBookById() {
        when(bookRepository.findById(sampleBook.getId())).thenReturn(Optional.of(sampleBook));

        Book result = bookService.getBookById(sampleBook.getId());

        assertEquals(result.getAuthor(), "ME"); // Ensure correct book is returned
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty()); // No book found

        // Expecting IdNotFoundException when book with id 2 is requested
        Exception exception = assertThrows(IdNotFoundException.class, () -> bookService.getBookById(2L));

        assertEquals("Book with id:2 not Found", exception.getMessage()); // Verify error message
        verify(bookRepository, times(1)).findById(2L);
    }

    @Test
    void testUpdateBook() {
        Book updatedDetails = new Book();
        updatedDetails.setAuthor("New Author");
        updatedDetails.setTitle("New Title");
        updatedDetails.setPrice(BigDecimal.valueOf(25.99));
        updatedDetails.setPublishedDate(LocalDate.of(2010, 5, 20));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(sampleBook)); // Mock existing book
        when(bookRepository.save(any(Book.class))).thenReturn(sampleBook); // Mock save()

        Book updatedBook = bookService.updateBook(1L, updatedDetails);

        // Verify updates
        assertEquals("New Author", updatedBook.getAuthor());
        assertEquals("New Title", updatedBook.getTitle());
        assertEquals(BigDecimal.valueOf(25.99), updatedBook.getPrice());
        assertEquals(LocalDate.of(2010, 5, 20), updatedBook.getPublishedDate());

        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(sampleBook);
    }

    @Test
    void testUpdateBook_NotFound() {
        Book updatedDetails = new Book();
        updatedDetails.setAuthor("New Author");

        when(bookRepository.findById(1L)).thenReturn(Optional.empty()); // No book found

        // Expecting IdNotFoundException when updating non-existent book
        Exception exception = assertThrows(IdNotFoundException.class, () -> bookService.updateBook(1L, updatedDetails));

        assertEquals("Book with id:1 not Found", exception.getMessage());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteBookById_NotFound() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty()); // No book found

        // Expecting IdNotFoundException when deleting non-existent book
        Exception exception = assertThrows(IdNotFoundException.class, () -> bookService.deleteBookById(2L));

        assertEquals("Book not Found", exception.getMessage());
        verify(bookRepository, times(1)).findById(2L);
        verify(bookRepository, never()).delete(any()); // Ensure delete() is never called
    }
}
