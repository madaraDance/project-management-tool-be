package com.example.project_management_tool.application.dto.auth;

import com.example.project_management_tool.application.dto.user.UserReadDTO;
import com.example.project_management_tool.domain.model.Company;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = false)
public class SignupReadDTO {
    @NotNull(message = "User information can not be null")
    private UserReadDTO userReadDTO;

    @NotNull(message = "Company information can not be null")
    private Company company;
}
