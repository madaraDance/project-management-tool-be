package com.example.project_management_tool.presentation.controller;

import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleCreateDTO;
import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleReadDTO;
import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleUpdateDTO;
import com.example.project_management_tool.domain.model.CompanyUserRole;
import com.example.project_management_tool.domain.service.ICompanyUserRoleService;
import com.example.project_management_tool.presentation.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/companies/{companyId}/managers")
public class CompanyUserRoleController {
    private final ICompanyUserRoleService iCompanyUserRoleService;

    public CompanyUserRoleController(ICompanyUserRoleService iCompanyUserRoleService) {
        this.iCompanyUserRoleService = iCompanyUserRoleService;
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<CompanyUserRoleReadDTO>>> getAllCompanyManagers(@PathVariable @Valid UUID companyId) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), iCompanyUserRoleService.getAllCompanyUserRoles(companyId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<CompanyUserRoleReadDTO>> createCompanyManager(
            @PathVariable @Valid UUID companyId,
            @RequestBody CompanyUserRoleCreateDTO companyUserRoleCreateDTO) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), iCompanyUserRoleService.createCompanyUserRole(companyId, companyUserRoleCreateDTO)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GlobalResponse<CompanyUserRoleReadDTO>> updateCompanyManager(
            @PathVariable @Valid UUID id,
            @RequestBody CompanyUserRoleUpdateDTO companyUserRoleUpdateDTO) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), iCompanyUserRoleService.update(id, companyUserRoleUpdateDTO)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<CompanyUserRole>> deleteCompanyManager(@PathVariable @Valid UUID id) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.NO_CONTENT.value(), iCompanyUserRoleService.getCompanyUserRoleById(id)), HttpStatus.NO_CONTENT);
    }

}
