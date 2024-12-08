package com.example.project_management_tool.presentation.controller;

import com.example.project_management_tool.application.dto.company.CompanyCreateDTO;
import com.example.project_management_tool.application.dto.company.CompanyUpdateDTO;
import com.example.project_management_tool.domain.model.Company;
import com.example.project_management_tool.domain.service.ICompanyService;
import com.example.project_management_tool.presentation.shared.GlobalResponse;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private final ICompanyService iCompanyService;

    public CompanyController(ICompanyService iCompanyService) {
        this.iCompanyService = iCompanyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<Company>> getCompany(@PathVariable @Valid UUID id) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(),iCompanyService.getCompanyById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GlobalResponse<Company>> createCompany(@RequestBody CompanyCreateDTO companyCreateDTO) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.CREATED.value(),iCompanyService.createCompany(companyCreateDTO)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GlobalResponse<Company>> updateCompany(@PathVariable @Valid UUID id, @RequestBody CompanyUpdateDTO companyUpdateDTO) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(),iCompanyService.updateCompany(id, companyUpdateDTO)), HttpStatus.OK);
    }

}
