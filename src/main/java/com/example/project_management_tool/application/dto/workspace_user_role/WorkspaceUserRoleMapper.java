package com.example.project_management_tool.application.dto.workspace_user_role;

import com.example.project_management_tool.domain.model.WorkspaceUserRole;
import com.example.project_management_tool.presentation.shared.error.CustomNoFieldsToUpdateException;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceUserRoleMapper {

    public WorkspaceUserRole updateDTOtoWorkspaceUserRole(WorkspaceUserRole wurFromDb, WorkspaceUserRoleUpdateDTO dto) {
        WorkspaceUserRole wurToSave = WorkspaceUserRole
                .builder()
                .id(wurFromDb.getId())
                .workspaceId(wurFromDb.getWorkspaceId())
                .userId(wurFromDb.getUserId())
                .roleId(dto.getRoleId())
                .createdAt(wurFromDb.getCreatedAt())
                .updatedAt(wurFromDb.getUpdatedAt())
                .build();

        if (hasSameFields(wurFromDb, wurToSave)) {
            throw new CustomNoFieldsToUpdateException("No fields to update");
        }

        return wurToSave;
    }

    public boolean hasSameFields(WorkspaceUserRole wurFromDb, WorkspaceUserRole wurToSave) {

        return wurFromDb.getRoleId().equals(wurToSave.getRoleId());

    }
}
