package com.example.project_management_tool.presentation.controller;

import com.example.project_management_tool.application.dto.workspace.WorkspaceCreateDTO;
import com.example.project_management_tool.application.dto.workspace.WorkspaceUpdateDTO;
import com.example.project_management_tool.domain.model.Workspace;
import com.example.project_management_tool.domain.service.IWorkspaceService;
import com.example.project_management_tool.presentation.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/companies/{companyId}/workspaces")
public class WorkspaceController {

    private final IWorkspaceService iWorkspaceService;

    public WorkspaceController(IWorkspaceService iWorkspaceService){
        this.iWorkspaceService = iWorkspaceService;
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Workspace>>> getAllByCompanyId(@PathVariable @Valid UUID companyId) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), iWorkspaceService.getAllByCompanyId(companyId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<Workspace>> createWorkspace(@PathVariable @Valid UUID companyId, @RequestBody @Valid WorkspaceCreateDTO workspaceCreateDTO) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), iWorkspaceService.createWorkspace(companyId, workspaceCreateDTO)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GlobalResponse<Workspace>> updateWorkspace(@PathVariable @Valid UUID id, @RequestBody @Valid WorkspaceUpdateDTO workspaceUpdateDTO) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), iWorkspaceService.updateWorkspace(id, workspaceUpdateDTO)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<Void>> deleteWorkspace(@PathVariable @Valid UUID id) {
        iWorkspaceService.deleteWorkspace(id);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), null), HttpStatus.NO_CONTENT);
    }

}
