package com.example.project_management_tool.domain.repository.company_user_role_abstraction;

import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleReadDTO;
import com.example.project_management_tool.domain.model.CompanyUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ICompanyUserRoleRepositoryJpa extends JpaRepository<CompanyUserRole, UUID> {
    @Query(value = "SELECT cur.id AS id, u.first_name AS firstName, u.last_name AS lastName, r.name AS roleName " +
            "FROM company_user_role cur " +
            "JOIN \"user\" u ON cur.user_id = u.id " +
            "JOIN role r ON cur.role_id = r.id " +
            "WHERE cur.company_id = :companyId",
            nativeQuery = true)
    List<Object[]> findByCompanyId(@Param("companyId") UUID companyId);
}
