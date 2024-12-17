package com.example.project_management_tool.application.service;

import com.example.project_management_tool.application.dto.project.ProjectCreateDTO;
import com.example.project_management_tool.application.dto.project.ProjectMapper;
import com.example.project_management_tool.application.dto.project.ProjectUpdateDTO;
import com.example.project_management_tool.domain.model.Project;
import com.example.project_management_tool.domain.repository.project_abstraction.IProjectRepository;
import com.example.project_management_tool.domain.repository.workspace_abstraction.IWorkspaceRepository;
import com.example.project_management_tool.domain.service.IProjectService;
import com.example.project_management_tool.presentation.shared.error.CustomResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements IProjectService {

    private final IProjectRepository iProjectRepository;

    private final  IWorkspaceRepository iWorkspaceRepository;

    private final ProjectMapper projectMapper;

    public ProjectServiceImpl(
            IProjectRepository iProjectRepository,
            IWorkspaceRepository iWorkspaceRepository,
            ProjectMapper projectMapper
    ) {
        this.iProjectRepository = iProjectRepository;
        this.iWorkspaceRepository = iWorkspaceRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public List<Project> getAllByWorkspaceId(UUID workspaceId) {
        iWorkspaceRepository
                .findOneById(workspaceId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Workspace with id: " + workspaceId + " was not found."));

        return iProjectRepository.findAllByWorkspaceId(workspaceId);
    }

    @Override
    public Project createProject(UUID workspaceId, ProjectCreateDTO createDto) {
        iWorkspaceRepository.findOneById(workspaceId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Workspace with id: " + workspaceId + " was not found."));

        return iProjectRepository.saveProject(
                projectMapper.createDtoToProject(workspaceId, createDto)
        );
    }

    @Override
    public Project updateProject(UUID id, ProjectUpdateDTO updateDTO) {
        Project projectFromDb = iProjectRepository.findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Project with id: " + id + " was not found."));

        Project projectToSave = projectMapper.updateDtoToProject(projectFromDb, updateDTO);

        return iProjectRepository.saveProject(projectToSave);
    }


    @Override
    public void deleteProjectById(UUID id) {
        Project projectFromDb = iProjectRepository.findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Project with id: " + id + " was not found."));

        iProjectRepository.deleteProjectById(id);
    }
}
