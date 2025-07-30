package com.example.test_telros.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailsDTO {
    @NotBlank
    private String lastName;
    @NotBlank
    private String firstName;
    @NotBlank
    private String middleName;
    @NotNull
    private LocalDate birthDate;
}
