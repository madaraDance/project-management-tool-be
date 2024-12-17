package com.example.project_management_tool.presentation.controller;


import com.example.project_management_tool.application.dto.project.ProjectCreateDTO;
import com.example.project_management_tool.application.dto.project.ProjectUpdateDTO;
import com.example.project_management_tool.domain.model.Project;
import com.example.project_management_tool.domain.service.IProjectService;
import com.example.project_management_tool.presentation.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("companies/{companyId}/workspaces/{workspaceId}/projects")
public class ProjectController {

    @Autowired
    private IProjectService iProjectService;

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Project>>> getAllByWorkspaceId(@PathVariable @Valid UUID workspaceId) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), iProjectService.getAllByWorkspaceId(workspaceId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<Project>> createProject(@PathVariable @Valid UUID workspaceId, @RequestBody @Valid ProjectCreateDTO dto) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), iProjectService.createProject(workspaceId, dto)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GlobalResponse<Project>> updateProject(@PathVariable @Valid UUID id, @RequestBody @Valid ProjectUpdateDTO dto) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), iProjectService.updateProject(id, dto)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<String>> deleteProject(@PathVariable @Valid UUID id) {
        iProjectService.deleteProjectById(id);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), "Project deleted."), HttpStatus.OK);
    }


}
