package com.example.project_management_tool.data_factory;

import com.example.project_management_tool.domain.repository.role_abstraction.IRoleRepository;
import com.example.project_management_tool.domain.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoleDataFactory {

    @Autowired
    IRoleRepository iRoleRepository;

    public Role createRole(String name, UUID companyId, boolean doSave) {
        Role role = Role.builder()
                .name(name)
                .isSystemRole(false)
                .companyId(companyId)
                .build();

        if (doSave) {
            return iRoleRepository.save(role);
        }

        return role;
    }

    public void deleteAll() {
        iRoleRepository.deleteAll();
    }
}
