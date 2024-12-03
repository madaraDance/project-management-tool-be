package com.example.project_management_tool.application.dto.company_user_role;

import com.example.project_management_tool.domain.model.CompanyUserRole;
import com.example.project_management_tool.domain.model.Role;
import com.example.project_management_tool.domain.model.User;
import com.example.project_management_tool.presentation.shared.error.CustomNoFieldsToUpdateException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CompanyUserRoleMapper {
    public CompanyUserRole mapCreateDtoToCompanyUserRole(UUID companyId,
        CompanyUserRoleCreateDTO companyUserRoleCreateDTO) {

        return CompanyUserRole
                .builder()
                .companyId(companyId)
                .userId(companyUserRoleCreateDTO.getUserId())
                .roleId(companyUserRoleCreateDTO.getRoleId())
                .build();

    }

    public CompanyUserRole mapUpdateDtoToCompanyUserRole(CompanyUserRole companyUserRoleFromDb,
        CompanyUserRoleUpdateDTO companyUserRoleUpdateDTO) {
        CompanyUserRole companyUserRoleToSave = CompanyUserRole
                .builder()
                .id(companyUserRoleFromDb.getId())
                .companyId(companyUserRoleFromDb.getCompanyId())
                .userId(companyUserRoleUpdateDTO.getUserId())
                .roleId(companyUserRoleUpdateDTO.getRoleId())
                .createdAt(companyUserRoleFromDb.getCreatedAt())
                .updatedAt(companyUserRoleFromDb.getUpdatedAt())
                .build();

        if (hasSameFields(companyUserRoleFromDb, companyUserRoleToSave)){
           throw new CustomNoFieldsToUpdateException("No fields to update");
        }

        return companyUserRoleToSave;

    }

    public CompanyUserRoleReadDTO mapCompanyUserRoleToReadDto(UUID id, User user, Role role) {
        return CompanyUserRoleReadDTO
                .builder()
                .id(id)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roleName(role.getName())
                .build();
    }

    public boolean hasSameFields(CompanyUserRole companyUserRoleFromDb, CompanyUserRole companyUserRoleToSave) {
        return companyUserRoleFromDb.getUserId().equals(companyUserRoleToSave.getUserId())
                && companyUserRoleFromDb.getRoleId().equals(companyUserRoleToSave.getRoleId());
    }

}
