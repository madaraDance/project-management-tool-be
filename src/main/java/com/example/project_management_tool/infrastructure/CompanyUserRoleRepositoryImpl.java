package com.example.project_management_tool.infrastructure;

import com.example.project_management_tool.domain.model.CompanyUserRole;
import com.example.project_management_tool.domain.repository.company_user_role_abstraction.ICompanyUserRoleRepository;
import com.example.project_management_tool.domain.repository.company_user_role_abstraction.ICompanyUserRoleRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CompanyUserRoleRepositoryImpl implements ICompanyUserRoleRepository {

    private final ICompanyUserRoleRepositoryJpa iCompanyUserRoleRepositoryJpa;

    public CompanyUserRoleRepositoryImpl(ICompanyUserRoleRepositoryJpa iCompanyUserRoleRepositoryJpa) {
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
    public Optional<CompanyUserRole> findOneByUserId(UUID userId) {
        return iCompanyUserRoleRepositoryJpa.findByUserId(userId);
    }

    @Override
    public CompanyUserRole save(CompanyUserRole companyUserRole) {
        return iCompanyUserRoleRepositoryJpa.save(companyUserRole);
    }

    @Override
    public void deleteById(UUID id) {
        iCompanyUserRoleRepositoryJpa.deleteById(id);
    }

    @Override
    public void deleteAll() {
        iCompanyUserRoleRepositoryJpa.deleteAll();
    }
}
