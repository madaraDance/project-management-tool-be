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

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$", message = " must contain only alphabetic characters.")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$", message = " must contain only alphabetic characters.")
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
