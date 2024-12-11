package com.example.project_management_tool.infrastructure;

import com.example.project_management_tool.domain.model.RolePermission;
import com.example.project_management_tool.domain.repository.role_permission_abstraction.IRolePermissionRepository;
import com.example.project_management_tool.domain.repository.role_permission_abstraction.IRolePermissionRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RolePermissionRepositoryImpl implements IRolePermissionRepository {

    private final IRolePermissionRepositoryJpa iRolePermissionRepositoryJpa;

    public RolePermissionRepositoryImpl(IRolePermissionRepositoryJpa iRolePermissionRepositoryJpa) {
        this.iRolePermissionRepositoryJpa = iRolePermissionRepositoryJpa;
    }

    @Override
    public RolePermission save(RolePermission rolePermission) {
        return iRolePermissionRepositoryJpa.save(rolePermission);
    }

    @Override
    public List<RolePermission> saveAll(List<RolePermission> permissions) {
        return iRolePermissionRepositoryJpa.saveAll(permissions);
    }

    @Override
    public Optional<RolePermission> findOneById(UUID id) {
        return iRolePermissionRepositoryJpa.findById(id);
    }

    @Override
    public List<RolePermission> findAllByRoleId(UUID roleId) {
        return iRolePermissionRepositoryJpa.findAllByRoleId(roleId);
    }

    @Override
    public List<String> findPermissionNamesByRoleId(UUID roleId) {
        return iRolePermissionRepositoryJpa.findPermissionNamesByRoleId(roleId);
    }

    @Override
    public void deleteById(UUID id) {
        iRolePermissionRepositoryJpa.deleteById(id);
    }
}
