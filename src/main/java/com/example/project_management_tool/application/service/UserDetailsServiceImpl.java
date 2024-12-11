package com.example.project_management_tool.application.service;

import com.example.project_management_tool.domain.model.CompanyUserRole;
import com.example.project_management_tool.domain.model.RolePermission;
import com.example.project_management_tool.domain.model.WorkspaceUserRole;
import com.example.project_management_tool.domain.repository.company_user_role_abstraction.ICompanyUserRoleRepository;
import com.example.project_management_tool.domain.repository.role_permission_abstraction.IRolePermissionRepository;
import com.example.project_management_tool.domain.repository.user_abstraction.IUserRepository;
import com.example.project_management_tool.domain.repository.workspace_user_role_abstraction.IWorkspaceUserRoleRepository;
import com.example.project_management_tool.presentation.shared.error.CustomResourceNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.project_management_tool.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository iUserRepository;
    private final IWorkspaceUserRoleRepository iWorkspaceUserRoleRepository;
    private final IRolePermissionRepository iRolePermissionRepository;
    private final ICompanyUserRoleRepository iCompanyUserRoleRepository;

    public UserDetailsServiceImpl(
            IUserRepository iUserRepository,
            IWorkspaceUserRoleRepository iWorkspaceUserRoleRepository,
            IRolePermissionRepository iRolePermissionRepository,
            ICompanyUserRoleRepository iCompanyUserRoleRepository
    ) {
        this.iUserRepository = iUserRepository;
        this.iWorkspaceUserRoleRepository = iWorkspaceUserRoleRepository;
        this.iRolePermissionRepository = iRolePermissionRepository;
        this.iCompanyUserRoleRepository = iCompanyUserRoleRepository;
    }

   public UserDetails loadUserAndAuthoritiesByUsername(String username, UUID workspaceId) {
       User user = iUserRepository
               .findOneByEmail(username)
               .orElseThrow(() -> new CustomResourceNotFoundException("User with email: " + username + " was not found."));

       List<GrantedAuthority> authorities = new ArrayList<>();

       CompanyUserRole companyUserRole = iCompanyUserRoleRepository.findOneByUserId(user.getId()).orElse(null);

       if (companyUserRole != null) {
           authorities = populateAuthorities(companyUserRole.getRoleId());
       } else {
           WorkspaceUserRole workspaceUserRole = iWorkspaceUserRoleRepository
                   .findOneByWorkspaceIdAndUserId(workspaceId, user.getId())
                   .orElseThrow(() -> new CustomResourceNotFoundException("User is not a member of the given workspace."));

           authorities = populateAuthorities(workspaceUserRole.getRoleId());
       }

       user.setAuthorities(authorities);

       return org.springframework.security.core.userdetails.User
               .builder()
               .username(user.getUsername())
               .password(user.getPassword())
               .authorities(authorities)
               .build();
    }

    public List<GrantedAuthority> populateAuthorities(UUID roleId) {
        List<String> permissionNames = iRolePermissionRepository.findPermissionNamesByRoleId(roleId);

        return permissionNames.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = iUserRepository
                .findOneByEmail(username)
                .orElseThrow(() -> new CustomResourceNotFoundException("User with email: " + username + " was not found."));
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
