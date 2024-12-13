package com.example.project_management_tool.domain.repository.workspace_abstraction;

import com.example.project_management_tool.domain.model.Workspace;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IWorkspaceRepository {
    Optional<Workspace> findOneById(UUID id);

    List<Workspace> findAllByCompanyId(UUID companyId);

    Workspace save(Workspace workspace);

    void deleteById(UUID id);

    void deleteAll();
}
