package com.example.project_management_tool.domain.repository.workspace_user_role_abstraction;

import com.example.project_management_tool.domain.model.WorkspaceUserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IWorkspaceUserRoleRepository {
    Optional<WorkspaceUserRole> findOneById(UUID id);

    List<WorkspaceUserRole> findAllByWorkspaceId(UUID workspaceId);

    WorkspaceUserRole saveWorkspaceUserRole(WorkspaceUserRole workspaceUserRole);

    void deleteWorkspaceUserRoleById(UUID id);
}
