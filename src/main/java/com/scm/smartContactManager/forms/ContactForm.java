package com.scm.smartContactManager.forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm.smartContactManager.validators.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class ContactForm {
    @NotBlank(message="name is required")
    private String name;

    @Email(message="Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message="Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message="Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    private String address;
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedInLink;

    @ValidFile(message = "Invalid file")
    private MultipartFile contactImage;

    private String picture;
}
