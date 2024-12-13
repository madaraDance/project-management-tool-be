package com.example.project_management_tool.application.service;

import com.example.project_management_tool.application.dto.workspace_user_role.WorkspaceUserRoleMapper;
import com.example.project_management_tool.application.dto.workspace_user_role.WorkspaceUserRoleReadDTO;
import com.example.project_management_tool.application.dto.workspace_user_role.WorkspaceUserRoleUpdateDTO;
import com.example.project_management_tool.domain.model.WorkspaceUserRole;
import com.example.project_management_tool.domain.repository.role_abstraction.IRoleRepository;
import com.example.project_management_tool.domain.repository.user_abstraction.IUserRepository;
import com.example.project_management_tool.domain.repository.workspace_abstraction.IWorkspaceRepository;
import com.example.project_management_tool.domain.repository.workspace_user_role_abstraction.IWorkspaceUserRoleRepository;
import com.example.project_management_tool.domain.service.IWorkspaceUserRoleService;
import com.example.project_management_tool.presentation.shared.error.CustomResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WorkspaceUserRoleServiceImpl implements IWorkspaceUserRoleService {

    private final IWorkspaceUserRoleRepository iWorkspaceUserRoleRepository;

    private final IUserRepository iUserRepository;

    private final IRoleRepository iRoleRepository;

    private final IWorkspaceRepository iWorkspaceRepository;

    private final WorkspaceUserRoleMapper workspaceUserRoleMapper;

    public WorkspaceUserRoleServiceImpl(
            IWorkspaceUserRoleRepository iWorkspaceUserRoleRepository,
            IUserRepository iUserRepository,
            IRoleRepository iRoleRepository,
            IWorkspaceRepository iWorkspaceRepository,
            WorkspaceUserRoleMapper workspaceUserRoleMapper
            ) {
        this.iWorkspaceUserRoleRepository = iWorkspaceUserRoleRepository;
        this.iUserRepository = iUserRepository;
        this.iRoleRepository = iRoleRepository;
        this.iWorkspaceRepository = iWorkspaceRepository;
        this.workspaceUserRoleMapper = workspaceUserRoleMapper;
    }

    @Override
    public List<WorkspaceUserRoleReadDTO> getAllByCompanyId(UUID companyId) {
        List<Object[]> results = iWorkspaceUserRoleRepository.findAllByCompanyId(companyId);

        return results.stream()
                .map(row -> WorkspaceUserRoleReadDTO
                        .builder()
                        .id((UUID) row[0])
                        .workspaceName((String) row[1])
                        .userFirstName((String) row[2])
                        .userLastName((String) row[3])
                        .roleName((String) row[4])
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public WorkspaceUserRole createWorkspaceUserRole(WorkspaceUserRole workspaceUserRole) {
        iUserRepository
                .findOneById(workspaceUserRole.getUserId())
                .orElseThrow(() -> new CustomResourceNotFoundException("User with id: " + workspaceUserRole.getUserId() + " was not found."));

        iWorkspaceRepository
                .findOneById(workspaceUserRole.getWorkspaceId())
                .orElseThrow(() -> new CustomResourceNotFoundException("Workspace with id: " + workspaceUserRole.getWorkspaceId() + " was not found."));

        iRoleRepository
                .findOneById(workspaceUserRole.getRoleId())
                .orElseThrow(() -> new CustomResourceNotFoundException("Role with id: " + workspaceUserRole.getRoleId() + " was not found."));

        return iWorkspaceUserRoleRepository.saveWorkspaceUserRole(workspaceUserRole);
    }

    @Override
    public WorkspaceUserRole updateWorkspaceUserRole(UUID id, WorkspaceUserRoleUpdateDTO updateDTO) {
        WorkspaceUserRole wurFromDb= iWorkspaceUserRoleRepository
                .findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Workspace User Role with id: " + id + " was not found."));

        iRoleRepository
                .findOneById(updateDTO.getRoleId())
                .orElseThrow(() -> new CustomResourceNotFoundException("Role with id: " + id + " was not found"));

        return iWorkspaceUserRoleRepository.saveWorkspaceUserRole(
            workspaceUserRoleMapper.updateDTOtoWorkspaceUserRole(wurFromDb, updateDTO)
        );

    }

    @Override
    public void deleteWorkspaceUserRole(UUID id) {
        iWorkspaceUserRoleRepository
                .findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Workspace User Role with id: " + id + " was not found."));

        iWorkspaceUserRoleRepository.deleteWorkspaceUserRoleById(id);
    }
}
