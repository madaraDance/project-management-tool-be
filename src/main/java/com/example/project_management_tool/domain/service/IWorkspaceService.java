package com.example.project_management_tool.domain.service;

import com.example.project_management_tool.application.dto.workspace.WorkspaceCreateDTO;
import com.example.project_management_tool.application.dto.workspace.WorkspaceUpdateDTO;
import com.example.project_management_tool.domain.model.Workspace;

import java.util.List;
import java.util.UUID;

public interface IWorkspaceService {

    Workspace getOneById(UUID id);

    List<Workspace> getAllByCompanyId(UUID companyId);

    Workspace createWorkspace(UUID companyId, WorkspaceCreateDTO workspaceCreateDTO);

    Workspace updateWorkspace(UUID id, WorkspaceUpdateDTO workspaceUpdateDTO);

    void deleteWorkspace(UUID id);

}
