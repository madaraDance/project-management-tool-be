package com.example.project_management_tool.application.dto.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class RoleUpdateDTO {

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[a-zA-Z ]+$", message = " must contain only alphabetic characters and spaces.")
    private String name;
}
