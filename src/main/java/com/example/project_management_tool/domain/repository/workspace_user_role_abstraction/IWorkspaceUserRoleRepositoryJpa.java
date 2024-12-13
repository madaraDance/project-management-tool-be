package com.example.project_management_tool.domain.repository.workspace_user_role_abstraction;

import com.example.project_management_tool.domain.model.WorkspaceUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IWorkspaceUserRoleRepositoryJpa extends JpaRepository<WorkspaceUserRole, UUID> {
    List<WorkspaceUserRole> findByWorkspaceId(UUID workspaceId);

    Optional<WorkspaceUserRole> findByWorkspaceIdAndUserId(UUID workspaceId, UUID userId);

    @Query(value = "SELECT wur.id AS id, w.name AS workspaceName, u.first_name AS firstName, u.last_name AS lastName, r.name AS roleName " +
            "FROM workspace_user_role wur " +
            "JOIN \"user\" u ON wur.user_id = u.id " +
            "JOIN role r ON wur.role_id = r.id " +
            "JOIN workspace w ON wur.workspace_id = w.id " +
            "WHERE w.company_id = :companyId",
            nativeQuery = true)
    List<Object[]> findByCompanyId(@Param("companyId") UUID companyId);
}
