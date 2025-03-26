package org.example.onlinebookstore.repository;
import org.example.onlinebookstore.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
