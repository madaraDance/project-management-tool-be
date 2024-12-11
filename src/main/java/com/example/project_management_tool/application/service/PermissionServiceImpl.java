package com.example.project_management_tool.application.service;

import com.example.project_management_tool.domain.model.Permission;
import com.example.project_management_tool.domain.repository.permission_abstraction.IPermissionRepository;
import com.example.project_management_tool.domain.service.IPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements IPermissionService {

    public final IPermissionRepository iPermissionRepository;

    public PermissionServiceImpl(IPermissionRepository iPermissionRepository) {
        this.iPermissionRepository = iPermissionRepository;
    }

    @Override
    public List<Permission> findAll() {
        return iPermissionRepository.findAll();
    }
}
