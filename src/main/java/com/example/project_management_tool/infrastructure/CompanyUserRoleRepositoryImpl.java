package com.example.project_management_tool.infrastructure;

import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleReadDTO;
import com.example.project_management_tool.domain.model.CompanyUserRole;
import com.example.project_management_tool.domain.repository.company_user_role_abstraction.ICompanyUserRoleRepository;
import com.example.project_management_tool.domain.repository.company_user_role_abstraction.ICompanyUserRoleRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CompanyUserRoleRoleRepositoryImpl implements ICompanyUserRoleRepository {

    private final ICompanyUserRoleRepositoryJpa iCompanyUserRoleRepositoryJpa;

    public CompanyUserRoleRoleRepositoryImpl(ICompanyUserRoleRepositoryJpa iCompanyUserRoleRepositoryJpa) {
        this.iCompanyUserRoleRepositoryJpa = iCompanyUserRoleRepositoryJpa;
    }

    @Override
    public Optional<CompanyUserRole> findOneById(UUID id) {
        return iCompanyUserRoleRepositoryJpa.findById(id);
    }

    @Override
    public List<Object[]> findAllByCompanyId(UUID companyId) {
        return iCompanyUserRoleRepositoryJpa.findByCompanyId(companyId);
    }

    @Override
    public CompanyUserRole save(CompanyUserRole companyUserRole) {
        return iCompanyUserRoleRepositoryJpa.save(companyUserRole);
    }

    @Override
    public void deleteById(UUID id) {
        iCompanyUserRoleRepositoryJpa.deleteById(id);
    }
}
