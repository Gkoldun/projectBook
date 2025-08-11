package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserDTO {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
}
