package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CreateBookDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotBlank
    private String edition;
}
