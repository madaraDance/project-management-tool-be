package com.example.project_management_tool.application.dto.role;

import com.example.project_management_tool.domain.model.Role;
import com.example.project_management_tool.presentation.shared.error.CustomNoFieldsToUpdateException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoleMapper {

    public Role roleCreateDtoToRole(UUID companyId, RoleCreateDTO roleCreateDTO) {
        return Role
                .builder()
                .companyId(companyId)
                .name(roleCreateDTO.getName())
                .isSystemRole(false)
                .build();
    }

    public Role roleUpdateDtoToRole(Role roleFromDb, RoleUpdateDTO roleUpdateDTO) {
        Role roleToSave = Role
            .builder()
            .companyId(roleFromDb.getCompanyId())
            .name(roleUpdateDTO.getName() != null && !roleUpdateDTO.getName().isBlank()
                ? roleUpdateDTO.getName()
                : roleFromDb.getName())
            .isSystemRole(roleFromDb.getIsSystemRole())
            .createdAt(roleFromDb.getCreatedAt())
            .updatedAt(roleFromDb.getUpdatedAt())
            .build();

        if (hasSameFields(roleFromDb, roleToSave)) {
            throw new CustomNoFieldsToUpdateException("No fields to update");
        }

        return roleToSave;
    }

    public boolean hasSameFields(Role roleFromDb, Role roleToSave) {
        return roleFromDb.getName().equals(roleToSave.getName());
    }
}
