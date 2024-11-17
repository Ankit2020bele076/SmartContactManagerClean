package com.scm.smartContactManager.forms;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class UserForms {

    @NotBlank(message="Username is required")
    @Size(min=3, message="Minimum 3 characters are required")
    private String name;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min=5, message="Minimum 6 characters are required")
    private String password;

    @NotBlank(message="about is required")
    private String about;

    @Size(min=8, max=12, message="Invalid phone number")
    private String phoneNumber;
}
