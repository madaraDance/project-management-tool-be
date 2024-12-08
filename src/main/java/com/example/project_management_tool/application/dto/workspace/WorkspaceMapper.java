package com.example.project_management_tool.application.dto.workspace;

import com.example.project_management_tool.domain.model.User;
import com.example.project_management_tool.domain.model.Workspace;
import com.example.project_management_tool.presentation.shared.error.CustomNoFieldsToUpdateException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WorkspaceMapper {

    public Workspace mapCreateDtoToWorkspace(UUID companyId, WorkspaceCreateDTO createDTO) {
        return  Workspace.builder()
                .companyId(companyId)
                .name(createDTO.getName())
                .description(createDTO.getDescription())
                .build();
    }

    public Workspace mapUpdateDtoToWorkspace(Workspace workspaceFromDb, WorkspaceUpdateDTO updateDTO) {
        Workspace workspaceToSave = Workspace.builder()
                .id(workspaceFromDb.getId())
                .name(updateDTO.getName() == null
                        ? workspaceFromDb.getName()
                        : updateDTO.getName())
                .description(updateDTO.getDescription() == null
                        ? workspaceFromDb.getDescription()
                        : updateDTO.getDescription())
                .createdAt(workspaceFromDb.getCreatedAt())
                .updatedAt(workspaceFromDb.getUpdatedAt())
                .build();

        if (hasSameFields(workspaceFromDb, workspaceToSave)) {
            throw new CustomNoFieldsToUpdateException("No fields to update");
        }

        return workspaceToSave;
    }

    public boolean hasSameFields(Workspace workspaceFromDb,Workspace workspaceToSave) {

        return workspaceFromDb.getName().equals(workspaceToSave.getName())
                && workspaceFromDb.getDescription().equals(workspaceToSave.getDescription());
    }
}
