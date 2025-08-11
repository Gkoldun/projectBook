package com.example.demo.repository;

import com.example.demo.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByScoreBetween(double minScore, double maxScore, Pageable pageable);
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(concat('%', :title, '%')) " +
            "AND LOWER(b.author) LIKE LOWER(concat('%', :author, '%'))")
    Page<Book> findByTitleAndAuthor(
            @Param("title") String title,
            @Param("author") String author,
            Pageable pageable
    );
    List<Book> findByOwnerIdOrCurrentUserId(Long ownerId, Long currentUserId);
    List<Book> findByAvailableTrue();
}