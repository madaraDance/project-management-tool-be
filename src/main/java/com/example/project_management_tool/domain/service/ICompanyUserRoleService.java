package com.example.project_management_tool.domain.service;

import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleCreateDTO;
import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleReadDTO;
import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleUpdateDTO;
import com.example.project_management_tool.domain.model.CompanyUserRole;

import java.util.List;
import java.util.UUID;

public interface ICompanyUserRoleService {
    CompanyUserRole getCompanyUserRoleById(UUID id);

    List<CompanyUserRoleReadDTO> getAllCompanyUserRoles(UUID companyId);

    CompanyUserRoleReadDTO createCompanyUserRole(UUID companyId, CompanyUserRoleCreateDTO companyUserRoleCreateDTO);

    CompanyUserRoleReadDTO update(UUID id, CompanyUserRoleUpdateDTO companyUserRoleupdateDTO);

    void delete(UUID id);
}
