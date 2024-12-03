package com.example.project_management_tool.application.dto.company_user_role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = false)
public class CompanyUserRoleUpdateDTO {
    @NotNull
    @Valid
    private UUID userId;

    @NotNull
    @Valid
    private UUID roleId;
}
