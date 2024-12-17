package com.example.project_management_tool.domain.repository.project_abstraction;

import com.example.project_management_tool.domain.model.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProjectRepository {

    Optional<Project> findOneById(UUID id);

    List<Project> findAllByWorkspaceId(UUID workspaceId);

    Project saveProject(Project project);

    void deleteProjectById(UUID id);
}
