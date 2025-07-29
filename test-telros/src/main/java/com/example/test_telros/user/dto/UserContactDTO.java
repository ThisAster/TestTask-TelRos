package com.example.test_telros.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserContactDTO {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String phoneNumber;
}
