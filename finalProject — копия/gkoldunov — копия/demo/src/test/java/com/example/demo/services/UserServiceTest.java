package com.example.demo.services;

import com.example.demo.models.CreateUserDTO;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void addUser_ShouldSaveNewUser() {
        // Arrange
        CreateUserDTO dto = new CreateUserDTO("testUser", "password");
        User expectedUser = new User();
        expectedUser.setLogin("testUser");
        expectedUser.setPassword("password");

        when(userRepository.findByLogin("testUser")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);

        // Act
        User result = userService.addUser(dto);

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getLogin());
        assertEquals("password", result.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void addUser_ShouldThrowWhenLoginExists() {
        // Arrange
        CreateUserDTO dto = new CreateUserDTO("existingUser", "password");
        User existingUser = new User();

        when(userRepository.findByLogin("existingUser")).thenReturn(existingUser);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.addUser(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    void authenticate_ShouldReturnUserWhenCredentialsMatch() {
        // Arrange
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("correctPassword");

        when(userRepository.findByLogin("validUser")).thenReturn(user);

        // Act
        User result = userService.authenticate("validUser", "correctPassword");

        // Assert
        assertNotNull(result);
        assertEquals("validUser", result.getLogin());
    }

    @Test
    void authenticate_ShouldThrowWhenUserNotFound() {
        // Arrange
        when(userRepository.findByLogin("unknownUser")).thenReturn(null);

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> userService.authenticate("unknownUser", "anyPassword"));
    }

    @Test
    void authenticate_ShouldThrowWhenPasswordIncorrect() {
        // Arrange
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("correctPassword");

        when(userRepository.findByLogin("validUser")).thenReturn(user);

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> userService.authenticate("validUser", "wrongPassword"));
    }

    @Test
    void findByToken_ShouldReturnUser() {
        // Arrange
        User expectedUser = new User();
        expectedUser.setToken("validToken");

        when(userRepository.findByToken("validToken")).thenReturn(expectedUser);

        // Act
        User result = userService.findByToken("validToken");

        // Assert
        assertNotNull(result);
        assertEquals("validToken", result.getToken());
    }

    @Test
    void updateToken_ShouldUpdateToken() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        userService.updateToken(userId, "newToken");

        // Assert
        verify(userRepository).save(user);
        assertEquals("newToken", user.getToken());
    }

    @Test
    void updateToken_ShouldThrowWhenUserNotFound() {
        // Arrange
        Long userId = 99L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class,
                () -> userService.updateToken(userId, "anyToken"));
    }

    @Test
    void deleteUser_ShouldCallRepository() {
        // Arrange
        Long userId = 1L;
        doNothing().when(userRepository).deleteById(userId);

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository).deleteById(userId);
    }
}