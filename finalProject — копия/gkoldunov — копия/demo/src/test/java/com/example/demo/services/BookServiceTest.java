package com.example.demo.services;

import com.example.demo.models.*;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;
    private User testUser;
    private CreateBookDTO testBookDTO;
    private CreateReviewDTO testReviewDTO;






    @Test
    void findById_ShouldThrowExceptionWhenBookNotFound() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.findById(1L));
    }



    @Test
    void findBooksByScoreRange_ShouldReturnBooksInRange() {
        List<Book> books = Collections.singletonList(testBook);
        Page<Book> page = new PageImpl<>(books);
        when(bookRepository.findByScoreBetween(anyDouble(), anyDouble(), any(Pageable.class))).thenReturn(page);

        Page<Book> result = bookService.findBooksByScoreRange(4.0, 5.0, Pageable.unpaged());

        assertEquals(1, result.getContent().size());
    }

    @Test
    void findBooksByScoreRange_ShouldThrowExceptionForInvalidRange() {
        assertThrows(IllegalArgumentException.class,
                () -> bookService.findBooksByScoreRange(-1, 6, Pageable.unpaged()));
        assertThrows(IllegalArgumentException.class,
                () -> bookService.findBooksByScoreRange(5, 4, Pageable.unpaged()));
    }

    @Test
    void searchByTitle_ShouldReturnMatchingBooks() {
        List<Book> books = Collections.singletonList(testBook);
        Page<Book> page = new PageImpl<>(books);
        when(bookRepository.findByTitleContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(page);

        Page<Book> result = bookService.searchByTitle("Test", Pageable.unpaged());

        assertEquals(1, result.getContent().size());
    }

    @Test
    void searchByAuthor_ShouldReturnMatchingBooks() {
        List<Book> books = Collections.singletonList(testBook);
        Page<Book> page = new PageImpl<>(books);
        when(bookRepository.findByAuthorContainingIgnoreCase(anyString(), any(Pageable.class))).thenReturn(page);

        Page<Book> result = bookService.searchByAuthor("Author", Pageable.unpaged());

        assertEquals(1, result.getContent().size());
    }


    @Test
    void getUserBooks_ShouldReturnUserBooks() {
        when(bookRepository.findByOwnerIdOrCurrentUserId(anyLong(), anyLong()))
                .thenReturn(Collections.singletonList(testBook));

        List<Book> result = bookService.getUserBooks(1L);

        assertEquals(1, result.size());
    }


}