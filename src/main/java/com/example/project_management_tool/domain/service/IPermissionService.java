package com.example.project_management_tool.domain.service;

import com.example.project_management_tool.domain.model.Permission;

import java.util.List;

public interface IPermissionService {

    List<Permission> findAll();
}
