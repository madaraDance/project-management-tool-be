package com.example.project_management_tool.application.dto.company;

import com.example.project_management_tool.domain.model.Company;
import com.example.project_management_tool.domain.model.User;
import com.example.project_management_tool.presentation.shared.error.CustomNoFieldsToUpdateException;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public Company mapCreateDtoToCompany(CompanyCreateDTO companyCreateDTO) {
        return Company
                .builder()
                .companyName(companyCreateDTO.getCompanyName())
                .description(companyCreateDTO.getDescription())
                .build();
    }

    public Company mapUpdateDtoToCompany(Company companyFromDb, CompanyUpdateDTO companyUpdateDTO) {
        Company companyToUpdate = Company
                .builder()
                .companyName(companyUpdateDTO.getCompanyName() != null && !companyUpdateDTO.getCompanyName().isBlank()
                        ? companyUpdateDTO.getCompanyName()
                        : companyFromDb.getCompanyName())
                .description(companyUpdateDTO.getDescription() != null && !companyUpdateDTO.getDescription().isBlank()
                        ? companyUpdateDTO.getDescription()
                        : companyFromDb.getDescription())
                .createdAt(companyFromDb.getCreatedAt())
                .updatedAt(companyFromDb.getUpdatedAt())
                .build();

        if (hasSameFields(companyFromDb, companyToUpdate)) {
            throw new CustomNoFieldsToUpdateException("No fields to update");
        }

        return companyToUpdate;
    }

    public boolean hasSameFields(Company companyFromDb, Company companyToUpdate) {

        return companyFromDb.getCompanyName().equals(companyToUpdate.getCompanyName())
                && companyFromDb.getDescription().equals(companyToUpdate.getDescription());

    }
}
