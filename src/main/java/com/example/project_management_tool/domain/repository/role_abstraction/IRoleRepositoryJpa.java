package com.example.project_management_tool.domain.repository.role_abstraction;

import com.example.project_management_tool.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IRoleRepositoryJpa extends JpaRepository<Role, UUID> {

    List<Role> findByCompanyId(UUID companyId);
}