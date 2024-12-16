package com.example.project_management_tool.data_factory;

import com.example.project_management_tool.domain.model.CompanyUserRole;
import com.example.project_management_tool.domain.model.Permission;
import com.example.project_management_tool.domain.model.Role;
import com.example.project_management_tool.domain.model.RolePermission;
import com.example.project_management_tool.domain.repository.company_user_role_abstraction.ICompanyUserRoleRepository;
import com.example.project_management_tool.domain.repository.permission_abstraction.IPermissionRepository;
import com.example.project_management_tool.domain.repository.role_abstraction.IRoleRepository;
import com.example.project_management_tool.domain.repository.role_permission_abstraction.IRolePermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class CompanyUserRoleDataFactory {

    @Autowired
    public ICompanyUserRoleRepository iCompanyUserRoleRepository;

    @Autowired
    public IPermissionRepository iPermissionRepository;

    @Autowired
    IRolePermissionRepository iRolePermissionRepository;

    @Autowired
    IRoleRepository iRoleRepository;

    public CompanyUserRole createOwnerRoleWithPermissionsForUser(UUID userId, UUID companyId){
        Role role = iRoleRepository.save(
                Role.builder()
                        .companyId(companyId)
                        .name("OWNER")
                        .build()
        );

        List<Permission> permissions = iPermissionRepository.findAll();
        List<RolePermission> rps = new ArrayList<>();

        permissions.forEach(p -> rps.add(
                RolePermission.builder()
                        .permissionId(p.getId())
                        .roleId(role.getId())
                        .build()
        ));

        iRolePermissionRepository.saveAll(rps);

        return iCompanyUserRoleRepository.save(
                CompanyUserRole
                        .builder()
                        .roleId(role.getId())
                        .companyId(companyId)
                        .userId(userId)
                        .build()
        );
    }

    public CompanyUserRole createCompanyUserRole(UUID userId, UUID companyId, UUID roleId, boolean doSave) {
        CompanyUserRole cur = CompanyUserRole.builder()
                .userId(userId)
                .companyId(companyId)
                .roleId(roleId)
                .build();

        if (doSave) {
            return iCompanyUserRoleRepository.save(cur);
        }

        return cur;

    }

    public void deleteAll() {
        iCompanyUserRoleRepository.deleteAll();
        iRolePermissionRepository.deleteAll();
        iRoleRepository.deleteAll();
    }
}
