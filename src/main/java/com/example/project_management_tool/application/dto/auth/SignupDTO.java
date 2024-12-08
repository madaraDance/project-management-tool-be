package com.example.project_management_tool.application.dto.auth;

import com.example.project_management_tool.application.dto.company.CompanyCreateDTO;
import com.example.project_management_tool.application.dto.user.UserCreateDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = false)
public class SignupDTO {

    @NotNull(message = "User information can not be null")
    private UserCreateDTO userCreateDTO;

    @NotNull(message = "Company information can not be null")
    private CompanyCreateDTO companyCreateDTO;

}
