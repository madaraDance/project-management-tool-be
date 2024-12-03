package com.example.project_management_tool.application.service;

import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleCreateDTO;
import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleMapper;
import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleReadDTO;
import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleUpdateDTO;
import com.example.project_management_tool.domain.model.CompanyUserRole;
import com.example.project_management_tool.domain.model.Role;
import com.example.project_management_tool.domain.model.User;
import com.example.project_management_tool.domain.repository.company_abstraction.ICompanyRepository;
import com.example.project_management_tool.domain.repository.company_user_role_abstraction.ICompanyUserRoleRepository;
import com.example.project_management_tool.domain.repository.role_abstraction.IRoleRepository;
import com.example.project_management_tool.domain.repository.user_abstraction.IUserRepository;
import com.example.project_management_tool.domain.service.ICompanyUserRoleService;
import com.example.project_management_tool.presentation.shared.error.CustomResourceNotFoundException;
import com.example.project_management_tool.presentation.shared.error.CustomSystemRoleRequiredException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyUserRoleServiceImpl implements ICompanyUserRoleService {

    private final CompanyUserRoleMapper companyUserRoleMapper;

    private final ICompanyUserRoleRepository iCompanyUserRoleRepository;

    private final ICompanyRepository iCompanyRepository;

    private final IUserRepository iUserRepository;

    private final IRoleRepository iRoleRepository;

    public CompanyUserRoleServiceImpl(
        CompanyUserRoleMapper companyUserRoleMapper,
        ICompanyUserRoleRepository iCompanyUserRoleRepository,
        ICompanyRepository iCompanyRepository,
        IUserRepository iUserRepository,
        IRoleRepository iRoleRepository) {

        this.companyUserRoleMapper = companyUserRoleMapper;
        this.iCompanyUserRoleRepository = iCompanyUserRoleRepository;
        this.iCompanyRepository = iCompanyRepository;
        this.iUserRepository = iUserRepository;
        this.iRoleRepository = iRoleRepository;
    }

    @Override
    public CompanyUserRole getCompanyUserRoleById(UUID id) {
        return iCompanyUserRoleRepository
                .findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Company user role with id: " + id + " was not found."));
    }

    @Override
    public List<CompanyUserRoleReadDTO> getAllCompanyUserRoles(UUID companyId) {
        iCompanyRepository
                .findOneById(companyId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Company with id: " + companyId + " was not found."));

        List<Object[]> results = iCompanyUserRoleRepository.findAllByCompanyId(companyId);

        return results.stream()
                .map(row -> new CompanyUserRoleReadDTO(
                        (UUID) row[0],
                        (String) row[1],
                        (String) row[2],
                        (String) row[3]
                ))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyUserRoleReadDTO createCompanyUserRole(UUID companyId, CompanyUserRoleCreateDTO companyUserRoleCreateDTO) {
        iCompanyRepository
                .findOneById(companyId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Company with id: " + companyId + " was not found."));

        CompanyUserRole companyUserRoleToSave = companyUserRoleMapper.mapCreateDtoToCompanyUserRole(companyId, companyUserRoleCreateDTO);

        User userFromDb = iUserRepository
                .findOneById(companyUserRoleCreateDTO.getUserId())
                .orElseThrow(() -> new CustomResourceNotFoundException("User with id: " + companyUserRoleToSave.getUserId() + " was not found."));

        Role roleFromDb = iRoleRepository
                .findOneById(companyUserRoleToSave.getRoleId())
                .orElseThrow(() -> new CustomResourceNotFoundException("Role with id: " + companyUserRoleToSave.getRoleId() + " was not found."));

        if (!roleFromDb.getIsSystemRole()) {
            throw new CustomSystemRoleRequiredException("Role with id: " + roleFromDb.getId() + " is not a System Role.");
        }
        CompanyUserRole companyUserRoleFromDb = iCompanyUserRoleRepository.save(companyUserRoleToSave);
        return companyUserRoleMapper.mapCompanyUserRoleToReadDto(
                companyUserRoleFromDb.getId(),
                userFromDb,
                roleFromDb);
    }

    @Override
    public CompanyUserRoleReadDTO update(UUID id, CompanyUserRoleUpdateDTO companyUserRoleUpdateDTO) {
        CompanyUserRole companyUserRoleFromDb = getCompanyUserRoleById(id);

        CompanyUserRole companyUserRoleToSave = companyUserRoleMapper.mapUpdateDtoToCompanyUserRole(companyUserRoleFromDb, companyUserRoleUpdateDTO);

        User userFromDb = iUserRepository
                .findOneById(companyUserRoleToSave.getUserId())
                .orElseThrow(() -> new CustomResourceNotFoundException("User with id: " + companyUserRoleToSave.getUserId() + " was not found."));

        Role roleFromDb = iRoleRepository
                .findOneById(companyUserRoleToSave.getRoleId())
                .orElseThrow(() -> new CustomResourceNotFoundException("Role with id: " + companyUserRoleToSave.getRoleId() + " was not found."));

        if (!roleFromDb.getIsSystemRole()) {
            throw new CustomSystemRoleRequiredException("Role with id: " + roleFromDb.getId() + " is not a System Role.");
        }

        iCompanyUserRoleRepository.save(companyUserRoleToSave);
        return companyUserRoleMapper.mapCompanyUserRoleToReadDto(id, userFromDb, roleFromDb);
    }

    @Override
    public void delete(UUID id) {
        iCompanyUserRoleRepository
                .findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("Company user role with id: " + id + " was not found."));

        iCompanyUserRoleRepository.deleteById(id);
    }
}
