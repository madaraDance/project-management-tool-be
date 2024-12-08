package com.example.project_management_tool.application.dto.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = false)
public class LoginDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
