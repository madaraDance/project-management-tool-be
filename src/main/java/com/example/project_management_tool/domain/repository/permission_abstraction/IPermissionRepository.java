package com.example.project_management_tool.domain.repository.permission_abstraction;

import com.example.project_management_tool.domain.model.Permission;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPermissionRepository {
    Optional<Permission> findOneById(UUID id);

    Optional<Permission> findOneByName(String name);

    List<Permission> findAll();

    void saveAll(List<Permission> permissions);


}
