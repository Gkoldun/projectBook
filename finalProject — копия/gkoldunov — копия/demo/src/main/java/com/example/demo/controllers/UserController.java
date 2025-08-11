package com.example.demo.controllers;

import com.example.demo.models.CreateUserDTO;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import com.example.demo.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private  UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> signUpUser(@RequestBody CreateUserDTO userDTO) {
        try {
            User newUser = userService.addUser(userDTO);
            ApiResponse<User> res = new ApiResponse<>(
                    HttpStatus.CREATED.value(),
                    "User created",
                    newUser
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (RuntimeException e) {
            ApiResponse<User> res = new ApiResponse<>(
                    HttpStatus.BAD_REQUEST.value(),
                    e.getMessage(),
                    null
            );
            return ResponseEntity.badRequest().body(res);
        }
    }
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<User>> signInUser(@RequestBody CreateUserDTO userDTO) {
        try {
            User user = userService.authenticate(userDTO.getLogin(), userDTO.getPassword());
            ApiResponse<User> res = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "Login successful",
                    user
            );
            return ResponseEntity.ok(res);
        } catch (RuntimeException e) {
            ApiResponse<User> res = new ApiResponse<>(
                    HttpStatus.UNAUTHORIZED.value(),
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            ApiResponse<Void> res = new ApiResponse<>(
                    HttpStatus.OK.value(),
                    "User deleted successfully",
                    null
            );
            return ResponseEntity.ok(res);
        } catch (RuntimeException e) {
            ApiResponse<Void> res = new ApiResponse<>(
                    HttpStatus.NOT_FOUND.value(),
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
        }
    }
}
