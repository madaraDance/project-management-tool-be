package com.example.project_management_tool.domain.service;

import com.example.project_management_tool.application.dto.role.RoleCreateDTO;
import com.example.project_management_tool.application.dto.role.RoleUpdateDTO;
import com.example.project_management_tool.domain.model.Role;

import java.util.List;
import java.util.UUID;

public interface IRoleService {
    Role createRole(UUID companyId, RoleCreateDTO roleCreateDTO);

    Role updateRole(UUID id, RoleUpdateDTO roleUpdateDTO);

    Role getRoleById(UUID id);

    List<Role> getAllByCompanyId(UUID companyId);

    void deleteRole(UUID id);

}
