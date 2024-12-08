package com.example.project_management_tool.infrastructure.initializer;

import com.example.project_management_tool.domain.enums.PermissionName;
import com.example.project_management_tool.domain.enums.SystemRole;
import com.example.project_management_tool.domain.model.Permission;
import com.example.project_management_tool.domain.model.Role;
import com.example.project_management_tool.domain.model.RolePermission;
import com.example.project_management_tool.domain.repository.permission_abstraction.IPermissionRepository;
import com.example.project_management_tool.domain.repository.role_abstraction.IRoleRepository;
import com.example.project_management_tool.domain.repository.role_permission_abstraction.IRolePermissionRepository;
import com.example.project_management_tool.presentation.shared.error.CustomResourceNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Initializer {

    private final IPermissionRepository iPermissionRepository;
    private final IRoleRepository iRoleRepository;
    private final IRolePermissionRepository iRolePermissionRepository;

    public Initializer(
            IPermissionRepository iPermissionRepository,
            IRoleRepository iRoleRepository,
            IRolePermissionRepository iRolePermissionRepository) {
        this.iPermissionRepository = iPermissionRepository;
        this.iRoleRepository = iRoleRepository;
        this.iRolePermissionRepository = iRolePermissionRepository;
    }

    @PostConstruct
    public void initializePermissions() {

        Set<String> pNamesFromDb = new HashSet<>();
        List<Permission> permissionsToSave = new ArrayList<>();
        List<Permission> permissionsFromDb = iPermissionRepository.findAll();

        permissionsFromDb.forEach(p -> pNamesFromDb.add(p.getName()));


        for (PermissionName pName : PermissionName.values()) {
            if(pNamesFromDb.contains(pName.toString())) {
                continue;
            }

            UUID permissionUuid = UUID.fromString(pName.getUuid());
            permissionsToSave.add(Permission
                    .builder()
                    .id(permissionUuid)
                    .name(pName.toString())
                    .build());
        }

        iPermissionRepository.saveAll(permissionsToSave);
    }

    public void initializeSystemRolesForCompany(UUID companyId) {
        List<Role> rolesToSave = new ArrayList<>();

        for (SystemRole sRole : SystemRole.values()) {
            UUID sRoleUuid = UUID.fromString(sRole.getUuid());
            rolesToSave.add(Role
                    .builder()
                    .id(sRoleUuid)
                    .name(sRole.toString())
                    .companyId(companyId)
                    .isSystemRole(true)
                    .build());
        }

        List<Role> systemRolesFromDb = iRoleRepository.saveAll(rolesToSave);

        Role ownerRole = iRoleRepository
                .findOneByNameAndCompanyId(SystemRole.ADMIN.toString(), companyId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Role with name Admin was not found."));

        initializeOwnerPermissions(ownerRole.getId());
    }

    public void initializeOwnerPermissions(UUID ownerRoleId) {
        List<RolePermission> rolePermissionsToSave = new ArrayList<>();
        List<Permission> permissionsFromDb = iPermissionRepository.findAll();

        permissionsFromDb.forEach(p -> rolePermissionsToSave.add(RolePermission
                .builder()
                .permissionId(p.getId())
                .roleId(ownerRoleId)
                .build()));

        iRolePermissionRepository.saveAll(rolePermissionsToSave);
    }
}
