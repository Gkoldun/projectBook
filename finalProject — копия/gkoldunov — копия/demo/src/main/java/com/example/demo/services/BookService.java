package com.example.demo.services;

import com.example.demo.models.*;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    public BookService(BookRepository bookRepository, UserRepository userRepository, ReviewRepository reviewRepository){
        this.bookRepository=bookRepository;
        this.userRepository=userRepository;
        this.reviewRepository=reviewRepository;
    }
    @Transactional
    public Book addBook(CreateBookDTO bookDTO, Long ownerId) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setEdition(bookDTO.getEdition());
        book.setAvailable(true);
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        book.setOwner(owner);
        return bookRepository.save(book);
    }


    @Transactional
    public Book addReviewToBook(CreateReviewDTO reviewDTO,Long userId, Long bookId) {
        Review review = new Review();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь с ID " + userId + " не найден"));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Книга с ID " + bookId + " не найдена"));
        review.setBook(book);
        review.setUser(user);
        review.setDescription(reviewDTO.getDescription());
        review.setGrade(reviewDTO.getGrade());
        reviewRepository.save(review);
        if (book.getReviews() == null) {
            book.setReviews(new ArrayList<>());
        }
        book.getReviews().add(review);
        if (book.getScore() == 0) {
            book.setScore(review.getGrade());
        } else {
            double sum = book.getScore() * (book.getReviews().size() - 1);
            sum += review.getGrade();
            book.setScore(sum / (book.getReviews().size()));
        }
        return bookRepository.save(book);
    }


    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Книга с id:" + id+" не найдена"));
    }


    public Page<Book> changeAvailable(Book book){
        book.setAvailable(!(book.isAvailable()));
        Book savedBook = bookRepository.save(book);
        return new PageImpl<>(List.of(savedBook));
    }

    public Page<Book> findBooksByScoreRange(double minScore, double maxScore, Pageable pageable) {
        if (minScore < 0 || maxScore > 5 || minScore > maxScore) {
            throw new IllegalArgumentException("Некорректный интервал оценок. Допустимый диапазон: 0-5");
        }
        return bookRepository.findByScoreBetween(minScore, maxScore, pageable);
    }
    public Page<Book> searchByTitle(String title, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable);
    }
    public Page<Book> searchByAuthor(String author, Pageable pageable) {
        return bookRepository.findByAuthorContainingIgnoreCase(author, pageable);
    }

    public Page<Book> searchByTitleAndAuthor(String title, String author, Pageable pageable) {
        return bookRepository.findByTitleAndAuthor(title, author, pageable);
    }

    public List<Book> getUserBooks(Long userId) {
        return bookRepository.findByOwnerIdOrCurrentUserId(userId, userId);
    }



    public List<Book> getAllBooks() {
        List<Book> availableBooks = bookRepository.findByAvailableTrue();
        if (availableBooks == null || availableBooks.isEmpty()) {
            return List.of();
        }
        return availableBooks;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public void setCurrentUser(Book book, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        book.setCurrentUser(user);
    }

}
