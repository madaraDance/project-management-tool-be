package com.example.project_management_tool.domain.service;

import com.example.project_management_tool.application.dto.company.CompanyCreateDTO;
import com.example.project_management_tool.application.dto.company.CompanyUpdateDTO;
import com.example.project_management_tool.domain.model.Company;

import java.util.UUID;

public interface ICompanyService {

    Company getCompanyById(UUID id);

    Company createCompany(CompanyCreateDTO companyCreateDTO);

    Company updateCompany(UUID id, CompanyUpdateDTO companyUpdateDTO);
}
