package com.example.project_management_tool.application.dto.company_user_role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CompanyUserRoleReadDTO {
    private UUID id;

    private String firstName;

    private String lastName;

    private String roleName;
}
