package com.example.project_management_tool.domain.repository.role_permission_abstraction;

import com.example.project_management_tool.domain.model.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IRolePermissionRepositoryJpa extends JpaRepository<RolePermission, UUID> {

    Optional<RolePermission> findById(UUID id);

    List<RolePermission> findAllByRoleId(UUID roleId);
}
