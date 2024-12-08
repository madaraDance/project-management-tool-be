package com.example.project_management_tool.infrastructure;

import com.example.project_management_tool.domain.model.Permission;
import com.example.project_management_tool.domain.repository.permission_abstraction.IPermissionRepository;
import com.example.project_management_tool.domain.repository.permission_abstraction.IPermissionRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PermissionRepositoryImpl implements IPermissionRepository {

    private final IPermissionRepositoryJpa iPermissionRepositoryJpa;

    public PermissionRepositoryImpl(IPermissionRepositoryJpa iPermissionRepositoryJpa) {
        this.iPermissionRepositoryJpa = iPermissionRepositoryJpa;
    }

    @Override
    public Optional<Permission> findOneById(UUID id) {
        return iPermissionRepositoryJpa.findById(id);
    }

    @Override
    public Optional<Permission> findOneByName(String name) {
        return iPermissionRepositoryJpa.findByName(name);
    }

    @Override
    public List<Permission> findAll() {
        return iPermissionRepositoryJpa.findAll();
    }

    @Override
    public void saveAll(List<Permission> permissions) {
        iPermissionRepositoryJpa.saveAll(permissions);
    }
}
