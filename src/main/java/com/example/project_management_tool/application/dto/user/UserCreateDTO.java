package com.example.project_management_tool.application.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = false)
public class UserCreateDTO {

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$", message = " must contain only alphabetic characters.")
    private String firstName;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$", message = " must contain only alphabetic characters.")
    private String lastName;

    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;

}