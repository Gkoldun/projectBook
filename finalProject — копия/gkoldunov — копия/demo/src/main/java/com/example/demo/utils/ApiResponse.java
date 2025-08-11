package com.example.demo.utils;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {
    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private T data;
    private String error;

    public ApiResponse(int status, String message, T data) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(int status, String message, String error) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.error = error;
    }
}
