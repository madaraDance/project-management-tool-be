package com.example.project_management_tool.presentation.controller;

import com.example.project_management_tool.domain.model.Permission;
import com.example.project_management_tool.domain.service.IPermissionService;
import com.example.project_management_tool.presentation.shared.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("companies/{companyId}/permissions")
public class PermissionController {

    private final IPermissionService iPermissionService;

    public PermissionController(IPermissionService iPermissionService) {
        this.iPermissionService = iPermissionService;
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Permission>>> getAllPermissions() {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), iPermissionService.findAll()), HttpStatus.OK);
    }
}
