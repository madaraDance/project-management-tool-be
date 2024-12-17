package com.example.project_management_tool.domain.service;

import com.example.project_management_tool.application.dto.project.ProjectCreateDTO;
import com.example.project_management_tool.application.dto.project.ProjectUpdateDTO;
import com.example.project_management_tool.domain.model.Project;

import java.util.List;
import java.util.UUID;

public interface IProjectService {

    List<Project> getAllByWorkspaceId(UUID workspaceId);

    Project createProject(UUID workspaceId,ProjectCreateDTO DTO);

    Project updateProject(UUID id, ProjectUpdateDTO updateDTO);

    void deleteProjectById(UUID id);
}
