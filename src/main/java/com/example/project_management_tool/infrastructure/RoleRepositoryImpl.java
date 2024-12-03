package com.example.project_management_tool.infrastructure;

import com.example.project_management_tool.domain.model.Role;
import com.example.project_management_tool.domain.repository.role_abstraction.IRoleRepository;
import com.example.project_management_tool.domain.repository.role_abstraction.IRoleRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RoleRepositoryImpl implements IRoleRepository {

    private final IRoleRepositoryJpa iRoleRepositoryJpa;

    public RoleRepositoryImpl(IRoleRepositoryJpa iRoleRepositoryJpa) {
        this.iRoleRepositoryJpa = iRoleRepositoryJpa;
    }

    @Override
    public Role save(Role role) {
        return iRoleRepositoryJpa.save(role);
    }

    @Override
    public List<Role> findAllRolesByCompanyId(UUID companyId) {
        return iRoleRepositoryJpa.findByCompanyId(companyId);
    }

    @Override
    public void deleteById(UUID id) {
        iRoleRepositoryJpa.deleteById(id);
    }

    @Override
    public Optional<Role> findOneById(UUID id) {
        return iRoleRepositoryJpa.findById(id);
    }
}
