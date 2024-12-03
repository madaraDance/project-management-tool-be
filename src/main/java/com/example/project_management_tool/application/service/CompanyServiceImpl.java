package com.example.project_management_tool.application.service;

import com.example.project_management_tool.application.dto.company.CompanyCreateDTO;
import com.example.project_management_tool.application.dto.company.CompanyMapper;
import com.example.project_management_tool.application.dto.company.CompanyUpdateDTO;
import com.example.project_management_tool.domain.model.Company;
import com.example.project_management_tool.domain.repository.company_abstraction.ICompanyRepository;
import com.example.project_management_tool.domain.service.ICompanyService;
import com.example.project_management_tool.presentation.shared.error.CustomResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyServiceImpl implements ICompanyService {

    private CompanyMapper companyMapper;
    private ICompanyRepository iCompanyRepository;

    public CompanyServiceImpl(CompanyMapper companyMapper,
                              ICompanyRepository iCompanyRepository) {
        this.companyMapper = companyMapper;
        this.iCompanyRepository = iCompanyRepository;
    }

    @Override
    public Company getCompanyById(UUID id) {
        return iCompanyRepository
                .findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Company with id: " + id + " was not found."));
    }

    @Override
    public Company createCompany(CompanyCreateDTO companyCreateDTO) {
        return iCompanyRepository.saveCompany(companyMapper.mapCreateDtoToCompany(companyCreateDTO));

    }

    @Override
    public Company updateCompany(UUID id, CompanyUpdateDTO companyUpdateDTO) {
        Company companyToUpdate = iCompanyRepository
                .findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Company with id: " + id + " was not found."));

        return iCompanyRepository.saveCompany(companyMapper.mapUpdateDtoToCompany(companyToUpdate, companyUpdateDTO));
    }
}
