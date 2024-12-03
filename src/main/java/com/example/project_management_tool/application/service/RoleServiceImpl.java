package com.example.project_management_tool.application.service;

import com.example.project_management_tool.application.dto.role.RoleCreateDTO;
import com.example.project_management_tool.application.dto.role.RoleMapper;
import com.example.project_management_tool.application.dto.role.RoleUpdateDTO;
import com.example.project_management_tool.domain.model.Role;
import com.example.project_management_tool.domain.repository.company_abstraction.ICompanyRepository;
import com.example.project_management_tool.domain.repository.role_abstraction.IRoleRepository;
import com.example.project_management_tool.domain.service.IRoleService;
import com.example.project_management_tool.presentation.shared.error.CustomResourceNotFoundException;
import com.example.project_management_tool.presentation.shared.error.CustomSystemRoleModificationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements IRoleService {

    private final RoleMapper roleMapper;
    private final IRoleRepository iRoleRepository;
    private final ICompanyRepository iCompanyRepository;

    public RoleServiceImpl(RoleMapper roleMapper,
                           IRoleRepository iRoleRepository,
                           ICompanyRepository iCompanyRepository) {
        this.roleMapper = roleMapper;
        this.iRoleRepository = iRoleRepository;
        this.iCompanyRepository = iCompanyRepository;
    }

    @Override
    public Role createRole(UUID companyId, RoleCreateDTO roleCreateDTO) {
        iCompanyRepository
                .findOneById(companyId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Company with id: " + companyId + " was not found"));

        return iRoleRepository.save(roleMapper.roleCreateDtoToRole(companyId, roleCreateDTO));
    }

    @Override
    public Role updateRole(UUID id, RoleUpdateDTO roleUpdateDTO) {
        Role roleFromDb = iRoleRepository
                .findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Role with id: " + id + " was not found"));

        if (roleFromDb.getIsSystemRole()) {
            throw new CustomSystemRoleModificationException("System Roles can not be updated.");
        }

        return iRoleRepository.save(roleMapper.roleUpdateDtoToRole(roleFromDb, roleUpdateDTO));
    }

    @Override
    public Role getRoleById(UUID id) {
        return iRoleRepository
                .findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Role with id: " + id + " was not found"));
    }

    @Override
    public List<Role> getAllByCompanyId(UUID companyId) {
        iCompanyRepository
                .findOneById(companyId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Company with id: " + companyId + " was not found"));

        return iRoleRepository.findAllRolesByCompanyId(companyId);
    }

    @Override
    public void deleteRole(UUID id) {
        Role roleFromDb = iRoleRepository
                .findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Role with id: " + id + " was not found"));

        if (roleFromDb.getIsSystemRole()) {
            throw new CustomSystemRoleModificationException("System Roles can not be updated.");
        }

        iRoleRepository.deleteById(id);
    }
}
