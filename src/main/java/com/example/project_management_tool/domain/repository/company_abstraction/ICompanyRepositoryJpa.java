package com.example.project_management_tool.domain.repository.company_abstraction;

import com.example.project_management_tool.domain.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICompanyRepositoryJpa extends JpaRepository<Company, UUID> {

}
