package com.example.project_management_tool.infrastructure;

import com.example.project_management_tool.domain.model.WorkspaceUserRole;
import com.example.project_management_tool.domain.repository.workspace_user_role_abstraction.IWorkspaceUserRoleRepository;
import com.example.project_management_tool.domain.repository.workspace_user_role_abstraction.IWorkspaceUserRoleRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class WorkspaceUserRoleRepositoryImpl implements IWorkspaceUserRoleRepository {

    private final IWorkspaceUserRoleRepositoryJpa iWorkspaceUserRoleRepositoryJpa;

    public WorkspaceUserRoleRepositoryImpl(IWorkspaceUserRoleRepositoryJpa iWorkspaceUserRoleRepositoryJpa) {
        this.iWorkspaceUserRoleRepositoryJpa = iWorkspaceUserRoleRepositoryJpa;
    }

    @Override
    public Optional<WorkspaceUserRole> findOneById(UUID id) {
        return iWorkspaceUserRoleRepositoryJpa.findById(id);
    }

    @Override
    public Optional<WorkspaceUserRole> findOneByWorkspaceIdAndUserId(UUID workspaceId, UUID userId) {
        return iWorkspaceUserRoleRepositoryJpa.findByWorkspaceIdAndUserId(workspaceId, userId);
    }

    @Override
    public List<WorkspaceUserRole> findAllByWorkspaceId(UUID workspaceId) {
        return iWorkspaceUserRoleRepositoryJpa.findByWorkspaceId(workspaceId);
    }

    @Override
    public List<Object[]> findAllByCompanyId(UUID companyId) {
        return iWorkspaceUserRoleRepositoryJpa.findByCompanyId(companyId);
    }

    @Override
    public WorkspaceUserRole saveWorkspaceUserRole(WorkspaceUserRole workspaceUserRole) {
        return iWorkspaceUserRoleRepositoryJpa.save(workspaceUserRole);
    }

    @Override
    public void deleteWorkspaceUserRoleById(UUID id) {
        iWorkspaceUserRoleRepositoryJpa.findById(id);
    }
}
