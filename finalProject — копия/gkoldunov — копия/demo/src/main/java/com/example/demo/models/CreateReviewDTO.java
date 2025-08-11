package com.example.demo.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateReviewDTO {
    @NotBlank
    private String description;
    @Min(1)
    @Max(5)
    @NotBlank
    private int grade;
}
