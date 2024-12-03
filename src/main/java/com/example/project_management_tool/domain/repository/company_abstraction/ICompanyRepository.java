package com.example.project_management_tool.domain.repository.company_abstraction;


import com.example.project_management_tool.domain.model.Company;

import java.util.Optional;
import java.util.UUID;

public interface ICompanyRepository {
    Optional<Company> findOneById(UUID id);

    Company saveCompany(Company company);

}
