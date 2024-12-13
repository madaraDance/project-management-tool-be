package com.example.project_management_tool.domain.repository.workspace_user_role_abstraction;

import com.example.project_management_tool.domain.model.WorkspaceUserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IWorkspaceUserRoleRepository {
    Optional<WorkspaceUserRole> findOneById(UUID id);

    Optional<WorkspaceUserRole> findOneByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);

    List<WorkspaceUserRole> findAllByWorkspaceId(UUID workspaceId);

    List<Object[]> findAllByCompanyId(UUID companyId);

    WorkspaceUserRole saveWorkspaceUserRole(WorkspaceUserRole workspaceUserRole);

    void deleteALl();

    void deleteWorkspaceUserRoleById(UUID id);
}
