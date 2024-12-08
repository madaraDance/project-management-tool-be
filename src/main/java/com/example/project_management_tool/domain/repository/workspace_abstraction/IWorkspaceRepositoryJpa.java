package com.example.project_management_tool.domain.repository.workspace_abstraction;

import com.example.project_management_tool.domain.model.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IWorkspaceRepositoryJpa extends JpaRepository<Workspace, UUID> {
    Optional<Workspace> findById(UUID id);

    List<Workspace> findByCompanyId(UUID companyId);
}
