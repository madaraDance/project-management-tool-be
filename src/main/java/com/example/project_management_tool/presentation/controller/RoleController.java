package com.example.project_management_tool.presentation.controller;


import com.example.project_management_tool.application.dto.role.RoleCreateDTO;
import com.example.project_management_tool.application.dto.role.RoleUpdateDTO;
import com.example.project_management_tool.domain.model.Role;
import com.example.project_management_tool.domain.service.IRoleService;
import com.example.project_management_tool.presentation.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/companies/{companyId}/roles")
public class RoleController {

    private final IRoleService iRoleService;

    public RoleController(IRoleService iRoleService) {
        this.iRoleService = iRoleService;
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Role>>> getAllByCompanyId(@PathVariable @Valid UUID companyId){
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), iRoleService.getAllByCompanyId(companyId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<Role>> createRole(@PathVariable @Valid UUID companyId, @RequestBody RoleCreateDTO roleCreateDTO){
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), iRoleService.createRole(companyId, roleCreateDTO)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GlobalResponse<Role>> updateRole(@PathVariable @Valid UUID id, @RequestBody RoleUpdateDTO roleUpdateDTO){
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), iRoleService.updateRole(id, roleUpdateDTO)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<String>> deleteRole(@PathVariable @Valid UUID id){
        iRoleService.deleteRole(id);

        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), "Role deleted."), HttpStatus.OK);
    }


}
