package com.example.project_management_tool.data_factory;

import com.example.project_management_tool.domain.model.Company;
import com.example.project_management_tool.domain.repository.company_abstraction.ICompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyDataFactory {

    @Autowired
    private ICompanyRepository iCompanyRepository;

    public Company createCompany(String name, boolean doSave) {
        Company c = Company.builder().companyName(name).build();

        if(doSave) {
            return iCompanyRepository.saveCompany(c);
        }

        return c;
    }

    public List<Company> createCompaniesAndSave(int count) {
        List<Company> companies = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            companies.add(
                    Company
                            .builder()
                            .companyName("Name " + i)
                            .build()
            );
        }

       return iCompanyRepository.saveAll(companies);
    }

    public void deleteAll() {
        iCompanyRepository.deleteAll();
    }
}
