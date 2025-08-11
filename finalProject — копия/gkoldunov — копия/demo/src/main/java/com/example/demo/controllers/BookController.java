package com.example.demo.controllers;

import com.example.demo.models.Book;
import com.example.demo.models.CreateBookDTO;
import com.example.demo.models.CreateReviewDTO;
import com.example.demo.services.BookService;
import com.example.demo.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<ApiResponse<Book>> addBook(@RequestBody CreateBookDTO bookDTO, @RequestParam Long ownerId) {
        Book newBook = bookService.addBook(bookDTO, ownerId);
        ApiResponse<Book> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Book created successfully",
                newBook
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        ApiResponse<List<Book>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Books retrieved successfully",
                books
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/take")
    public ResponseEntity<ApiResponse<Book>> takeBook(
            @RequestParam Long bookId,
            @RequestParam Long userId) {
        Book book = bookService.findById(bookId);

        if (!book.isAvailable()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),
                            "Книга уже занята",
                            null)
            );
        }

        book.setAvailable(false);

        bookService.setCurrentUser(book, userId);

        Book updatedBook = bookService.save(book);
        ApiResponse<Book> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Book taken successfully",
                updatedBook
        );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/user-books")
    public ResponseEntity<ApiResponse<List<Book>>> getUserBooks(@RequestParam Long userId) {
        List<Book> books = bookService.getUserBooks(userId);
        ApiResponse<List<Book>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "User's books retrieved successfully",
                books
        );
        return ResponseEntity.ok(response);
    }



    @PostMapping("/return")
    public ResponseEntity<ApiResponse<Book>> returnBook(
            @RequestParam Long bookId,
            @RequestParam Long userId) {
        Book book = bookService.findById(bookId);
        book.setAvailable(true);

        book.setCurrentUser(null);

        Book updatedBook = bookService.save(book);
        ApiResponse<Book> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Book returned successfully",
                updatedBook
        );
        return ResponseEntity.ok(response);
    }
    @PostMapping("/{bookId}/reviews")
    public ResponseEntity<ApiResponse<Book>> addReview(
            @PathVariable Long bookId,
            @RequestParam Long userId,
            @RequestBody CreateReviewDTO reviewDTO) {
        Book updatedBook = bookService.addReviewToBook(reviewDTO, userId, bookId);
        ApiResponse<Book> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Review added successfully",
                updatedBook
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}/availability")
    public ResponseEntity<ApiResponse<Book>> changeAvailability(@PathVariable Long id) {
        Book book = bookService.findById(id);
        book.setAvailable(!book.isAvailable());
        Book updatedBook = bookService.save(book);
        ApiResponse<Book> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Availability status changed",
                updatedBook
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/by-rating")
    public ResponseEntity<ApiResponse<Page<Book>>> findBooksByScoreRange(
            @RequestParam double min,
            @RequestParam double max,
            Pageable pageable) {
        Page<Book> books = bookService.findBooksByScoreRange(min, max, pageable);
        ApiResponse<Page<Book>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Books found by rating range",
                books
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/by-title")
    public ResponseEntity<ApiResponse<Page<Book>>> searchByTitle(
            @RequestParam String title,
            Pageable pageable) {
        Page<Book> books = bookService.searchByTitle(title, pageable);
        ApiResponse<Page<Book>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Books found by title",
                books
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search/by-author")
    public ResponseEntity<ApiResponse<Page<Book>>> searchByAuthor(
            @RequestParam String author,
            Pageable pageable) {
        Page<Book> books = bookService.searchByAuthor(author, pageable);
        ApiResponse<Page<Book>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Books found by author",
                books
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<Book>>> searchByTitleAndAuthor(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            Pageable pageable) {
        Page<Book> books;
        if (title != null && author != null) {
            books = bookService.searchByTitleAndAuthor(title, author, pageable);
        } else if (title != null) {
            books = bookService.searchByTitle(title, pageable);
        } else if (author != null) {
            books = bookService.searchByAuthor(author, pageable);
        } else {
            throw new IllegalArgumentException("No search parameters provided");
        }

        ApiResponse<Page<Book>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Books found",
                books
        );
        return ResponseEntity.ok(response);
    }
}
