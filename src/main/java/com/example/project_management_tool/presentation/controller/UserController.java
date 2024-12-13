package com.example.project_management_tool.presentation.controller;

import com.example.project_management_tool.application.dto.user.UserCreateDTO;
import com.example.project_management_tool.application.dto.user.UserReadDTO;
import com.example.project_management_tool.application.dto.user.UserUpdateDTO;
import com.example.project_management_tool.domain.service.IUserService;
import com.example.project_management_tool.presentation.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/companies/{companyId}/users")
public class UserController {
    public final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping
    public ResponseEntity<GlobalResponse<List<UserReadDTO>>> getAllByCompanyId(@PathVariable @Valid UUID companyId){
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), iUserService.getAllByCompanyId(companyId)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<UserReadDTO>> createUserForCompany(@PathVariable @Valid UUID companyId, @RequestBody @Valid UserCreateDTO userCreateDto) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(), iUserService.createUser(companyId, userCreateDto)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GlobalResponse<UserReadDTO>> updateUserForCompany(@PathVariable @Valid UUID id, @RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), iUserService.updateUser(id, userUpdateDTO)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<String>> deleteUserForCompany(@PathVariable @Valid UUID id) {
        iUserService.deleteUser(id);
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), "User deleted."), HttpStatus.OK);
    }
}
