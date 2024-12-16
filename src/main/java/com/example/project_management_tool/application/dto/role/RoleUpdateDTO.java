package com.example.project_management_tool.application.dto.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class RoleUpdateDTO {

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[A-Z_]+$", message = " must contain only uppercase letters and underscores.")
    private String name;
}
