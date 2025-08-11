package com.example.demo.services;

import com.example.demo.models.CreateUserDTO;
import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    @Transactional
    public User addUser(CreateUserDTO userDTO) {
        if (userRepository.findByLogin(userDTO.getLogin()) != null) {
            throw new RuntimeException("Пользователь с таким логином уже существует");
        }
        User user=new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        return userRepository.save(user);
    }

    public User authenticate(String login, String password) {
        User user = userRepository.findByLogin(login);
        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("Неверный логин или пароль");
        }
        return user;
    }

    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Transactional
    public void updateToken(Long userId, String newToken) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        user.setToken(newToken);
        userRepository.save(user);
    }
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
