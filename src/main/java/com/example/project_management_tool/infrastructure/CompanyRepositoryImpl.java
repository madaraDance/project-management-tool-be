package com.example.project_management_tool.infrastructure;

import com.example.project_management_tool.domain.model.Company;
import com.example.project_management_tool.domain.repository.company_abstraction.ICompanyRepository;
import com.example.project_management_tool.domain.repository.company_abstraction.ICompanyRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CompanyRepositoryImpl implements ICompanyRepository {
    private final ICompanyRepositoryJpa iCompanyRepositoryJpa;

    public CompanyRepositoryImpl(ICompanyRepositoryJpa iCompanyRepositoryJpa) {
        this.iCompanyRepositoryJpa = iCompanyRepositoryJpa;
    }

    @Override
    public Optional<Company> findOneById(UUID id) {
        return iCompanyRepositoryJpa.findById(id);
    }

    @Override
    public Company saveCompany(Company company) {
        return iCompanyRepositoryJpa.save(company);
    }

    @Override
    public List<Company> saveAll(List<Company> companies) {
        return iCompanyRepositoryJpa.saveAll(companies);
    }

    @Override
    public void deleteAll() {
        iCompanyRepositoryJpa.deleteAll();
    }
}
