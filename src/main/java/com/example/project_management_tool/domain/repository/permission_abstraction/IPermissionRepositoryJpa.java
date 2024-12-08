package com.example.project_management_tool.domain.repository.permission_abstraction;

import com.example.project_management_tool.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IPermissionRepositoryJpa extends JpaRepository<Permission, UUID> {
    Optional<Permission> findById(UUID id);

    Optional<Permission> findByName(String name);
}
