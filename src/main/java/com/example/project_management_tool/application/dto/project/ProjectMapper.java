package com.example.project_management_tool.application.dto.project;

import com.example.project_management_tool.domain.model.Project;
import com.example.project_management_tool.presentation.shared.error.CustomNoFieldsToUpdateException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProjectMapper {

    public Project createDtoToProject(UUID workspaceId, ProjectCreateDTO dto) {
        return Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .workspaceId(workspaceId)
                .build();
    }

    public Project updateDtoToProject(Project projectFromDb, ProjectUpdateDTO dto) {
        Project projectToSave = Project.builder()
                .id(projectFromDb.getId())
                .name(dto.getName() != null ? dto.getName() : projectFromDb.getName())
                .description(dto.getDescription() != null ? dto.getDescription() : projectFromDb.getDescription())
                .startDate(dto.getStartDate() != null ? dto.getStartDate() : projectFromDb.getStartDate())
                .endDate(dto.getEndDate() != null ? dto.getEndDate() : projectFromDb.getEndDate())
                .workspaceId(projectFromDb.getWorkspaceId())
                .createdAt(projectFromDb.getCreatedAt())
                .updatedAt(projectFromDb.getUpdatedAt())
                .build();

        if (hasSameFields(projectFromDb, projectToSave)) {
            throw new CustomNoFieldsToUpdateException("No fields to update.");
        }

        return projectToSave;


    }

    public boolean hasSameFields(Project projectFromDb, Project projectToSave) {
        return projectFromDb.getName().equals(projectToSave.getName()) &&
                projectFromDb.getDescription().equals(projectToSave.getDescription()) &&
                projectFromDb.getStartDate().equals(projectToSave.getStartDate()) &&
                projectFromDb.getEndDate().equals(projectToSave.getEndDate());
    }
}
