package com.example.project_management_tool.domain.repository.role_permission_abstraction;

import com.example.project_management_tool.domain.model.RolePermission;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRolePermissionRepository {

    RolePermission save(RolePermission rolePermission);

    List<RolePermission> saveAll(List<RolePermission> permissions);

    Optional<RolePermission> findOneById(UUID id);

    List<RolePermission> findAllByRoleId(UUID roleId);

    List<String> findPermissionNamesByRoleId(UUID roleId);

    void deleteById(UUID id);

    void deleteAll();
}
