package com.oauth2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserRegistrationDto(

        @NotEmpty(message = "User name must not be empty")
        String userName,

        String userMobileNo,

        @NotEmpty(message = "User Email must not be empty")
        @Email(message = "invalid Email Format")
        String userEmail,

        @NotEmpty(message = "User password must not be empty")
        String userPassword,
        String userRole
){}