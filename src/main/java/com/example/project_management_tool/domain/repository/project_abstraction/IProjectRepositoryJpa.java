package com.example.project_management_tool.domain.repository.project_abstraction;

import com.example.project_management_tool.domain.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProjectRepositoryJpa extends JpaRepository<Project, UUID> {

    List<Project> findByWorkspaceId(UUID workspaceId);
}
