package com.example.project_management_tool.domain.repository.company_user_role_abstraction;

import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleReadDTO;
import com.example.project_management_tool.domain.model.CompanyUserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICompanyUserRoleRepository {
    Optional<CompanyUserRole> findOneById(UUID id);

    List<Object[]> findAllByCompanyId(UUID companyId);

    CompanyUserRole save(CompanyUserRole companyUserRole);

    void deleteById(UUID id);
}