package com.example.project_management_tool.application.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class UserUpdateDTO {

    @Pattern(regexp = "^[a-zA-Z]+$", message = "must contain only alphabetic characters.")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]+$", message = "must contain only alphabetic characters.")
    private String lastName;

    private String password;
}
