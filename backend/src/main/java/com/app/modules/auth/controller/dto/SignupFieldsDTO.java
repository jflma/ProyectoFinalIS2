package com.app.modules.auth.controller.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupFieldsDTO(@NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Email String email,
        @NotBlank LocalDate birthDay,
        @NotBlank String username,
        @NotBlank String password) {
}
