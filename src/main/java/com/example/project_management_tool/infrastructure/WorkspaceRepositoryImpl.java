package com.example.project_management_tool.infrastructure;

import com.example.project_management_tool.domain.model.Workspace;
import com.example.project_management_tool.domain.repository.workspace_abstraction.IWorkspaceRepository;
import com.example.project_management_tool.domain.repository.workspace_abstraction.IWorkspaceRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class WorkspaceRepositoryImpl implements IWorkspaceRepository {

    private final IWorkspaceRepositoryJpa iWorkspaceRepositoryJpa;

    public WorkspaceRepositoryImpl(IWorkspaceRepositoryJpa iWorkspaceRepositoryJpa) {
        this.iWorkspaceRepositoryJpa = iWorkspaceRepositoryJpa;
    }

    @Override
    public Optional<Workspace> findOneById(UUID id) {
        return iWorkspaceRepositoryJpa.findById(id);
    }

    @Override
    public List<Workspace> findAllByCompanyId(UUID companyId) {
        return iWorkspaceRepositoryJpa.findByCompanyId(companyId);
    }

    @Override
    public Workspace save(Workspace workspace) {
        return iWorkspaceRepositoryJpa.save(workspace);
    }

    @Override
    public void deleteById(UUID id) {
        iWorkspaceRepositoryJpa.deleteById(id);
    }
}
