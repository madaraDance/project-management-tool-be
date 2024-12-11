package com.example.project_management_tool.domain.repository.workspace_user_role_abstraction;

import com.example.project_management_tool.domain.model.WorkspaceUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IWorkspaceUserRoleRepositoryJpa extends JpaRepository<WorkspaceUserRole, UUID> {
    List<WorkspaceUserRole> findByWorkspaceId(UUID workspaceId);

    Optional<WorkspaceUserRole> findByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);
}
