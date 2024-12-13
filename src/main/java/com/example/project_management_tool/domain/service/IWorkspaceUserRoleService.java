package com.example.project_management_tool.domain.service;

import com.example.project_management_tool.application.dto.workspace_user_role.WorkspaceUserRoleReadDTO;
import com.example.project_management_tool.application.dto.workspace_user_role.WorkspaceUserRoleUpdateDTO;
import com.example.project_management_tool.domain.model.WorkspaceUserRole;

import java.util.List;
import java.util.UUID;

public interface IWorkspaceUserRoleService {

    List<WorkspaceUserRoleReadDTO> getAllByCompanyId(UUID companyId);

    WorkspaceUserRole createWorkspaceUserRole(WorkspaceUserRole workspaceUserRole);

    WorkspaceUserRole updateWorkspaceUserRole(UUID id, WorkspaceUserRoleUpdateDTO updateDTO);

    void deleteWorkspaceUserRole(UUID id);
}
