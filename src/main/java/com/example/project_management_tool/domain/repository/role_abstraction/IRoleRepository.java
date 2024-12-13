package com.example.project_management_tool.domain.repository.role_abstraction;

import com.example.project_management_tool.domain.model.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository {
    Role save(Role role);

    List<Role> findAllRolesByCompanyId(UUID companyId);

    void deleteById(UUID id);

    Optional<Role> findOneById(UUID id);

    Optional<Role> findOneByNameAndCompanyId(String name, UUID companyId);

    List<Role> saveAll(List<Role> roles);

    void deleteAll();

}
