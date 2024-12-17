package com.example.project_management_tool.infrastructure;

import com.example.project_management_tool.domain.model.Project;
import com.example.project_management_tool.domain.repository.project_abstraction.IProjectRepository;
import com.example.project_management_tool.domain.repository.project_abstraction.IProjectRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProjectRepositoryImpl implements IProjectRepository {

    private final IProjectRepositoryJpa iProjectRepositoryJpa;

    public ProjectRepositoryImpl(IProjectRepositoryJpa iProjectRepositoryJpa) {
        this.iProjectRepositoryJpa = iProjectRepositoryJpa;
    }
    @Override
    public Optional<Project> findOneById(UUID id) {
        return iProjectRepositoryJpa.findById(id);
    }

    @Override
    public List<Project> findAllByWorkspaceId(UUID workspaceId) {
        return iProjectRepositoryJpa.findByWorkspaceId(workspaceId);
    }

    @Override
    public Project saveProject(Project project) {
        return iProjectRepositoryJpa.save(project);
    }

    @Override
    public void deleteProjectById(UUID id) {
        iProjectRepositoryJpa.deleteById(id);
    }
}
