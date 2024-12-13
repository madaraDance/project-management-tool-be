package com.example.project_management_tool.presentation.controller;

import com.example.project_management_tool.application.dto.workspace_user_role.WorkspaceUserRoleReadDTO;
import com.example.project_management_tool.application.dto.workspace_user_role.WorkspaceUserRoleUpdateDTO;
import com.example.project_management_tool.domain.model.WorkspaceUserRole;
import com.example.project_management_tool.domain.service.IWorkspaceUserRoleService;
import com.example.project_management_tool.presentation.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("companies/{companyId}/workspace_user_roles")
public class WorkspaceUserRoleController {

    private final IWorkspaceUserRoleService iWorkspaceUserRoleService;

    public WorkspaceUserRoleController( IWorkspaceUserRoleService iWorkspaceUserRoleService) {
        this.iWorkspaceUserRoleService = iWorkspaceUserRoleService;
    }

    @GetMapping
    private ResponseEntity<GlobalResponse<List<WorkspaceUserRoleReadDTO>>> getAllByCompanyId(@PathVariable @Valid UUID companyId) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), iWorkspaceUserRoleService.getAllByCompanyId(companyId)), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<GlobalResponse<WorkspaceUserRole>> createWorkspaceUserRole(@RequestBody @Valid WorkspaceUserRole wur) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), iWorkspaceUserRoleService.createWorkspaceUserRole(wur)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<GlobalResponse<WorkspaceUserRole>> updateWorkspaceUserRole(@PathVariable @Valid UUID id, @RequestBody @Valid WorkspaceUserRoleUpdateDTO updateDTO) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), iWorkspaceUserRoleService.updateWorkspaceUserRole(id, updateDTO)), HttpStatus.OK);
    }

    @DeleteMapping("/{ids}")
    private ResponseEntity<GlobalResponse<Void>> deleteWorkspaceUserRole(@PathVariable @Valid UUID id) {
        iWorkspaceUserRoleService.deleteWorkspaceUserRole(id);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null), HttpStatus.NO_CONTENT);
    }

}
