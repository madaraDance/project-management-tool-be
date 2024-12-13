package com.example.project_management_tool.integration;

import com.example.project_management_tool.domain.model.*;
import com.example.project_management_tool.domain.repository.company_abstraction.ICompanyRepository;
import com.example.project_management_tool.domain.repository.company_user_role_abstraction.ICompanyUserRoleRepository;
import com.example.project_management_tool.domain.repository.permission_abstraction.IPermissionRepository;
import com.example.project_management_tool.domain.repository.role_abstraction.IRoleRepository;
import com.example.project_management_tool.domain.repository.role_permission_abstraction.IRolePermissionRepository;
import com.example.project_management_tool.domain.repository.user_abstraction.IUserRepository;
import com.example.project_management_tool.presentation.config.JwtHelper;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UserControllerTest extends AbstractIntegrationTest {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;



    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private ICompanyRepository iCompanyRepository;

    @Autowired
    private ICompanyUserRoleRepository iCompanyUserRoleRepository;

    @Autowired
    private IRoleRepository iRoleRepository;

    @Autowired
    private IPermissionRepository iPermissionRepository;

    @Autowired
    private IRolePermissionRepository iRolePermissionRepository;

    @Autowired
    private JwtHelper jwtHelper;

    private UUID companyId;

    private String token;

    @BeforeEach
    public void generateCompanyUserAndToken(){
        User ownerUser = User
                .builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .password("SecurePass123")
                .build();

        Company company = Company
                .builder()
                .companyName("Test Company")
                .build();
        Company savedCompany = iCompanyRepository.saveCompany(company);

        companyId = savedCompany.getId();

        ownerUser.setCompanyId(companyId);

        User savedUser = userRepository.save(ownerUser);

        Role systemRole = iRoleRepository.save(Role.builder()
                .name("ADMIN")
                .companyId(companyId)
                .isSystemRole(true)
                .build());

        iCompanyUserRoleRepository.save(CompanyUserRole.builder()
                        .companyId(companyId)
                        .userId(savedUser.getId())
                        .roleId(systemRole.getId())
                .build());

        List<Permission> persmissons = iPermissionRepository.findAll();

        List<RolePermission> rps = new ArrayList<>();

        persmissons.forEach(p -> rps.add(RolePermission.builder()
                .roleId(systemRole.getId())
                .permissionId(p.getId())
                .build()));

        iRolePermissionRepository.saveAll(rps);


        token = jwtHelper.generateToken(savedUser, companyId);

    }

    @Test
    void testGetAllUsersFromCompany() throws Exception {
        // Simulate a GET request to your UserController's endpoint
        mockMvc.perform(get("/companies/{companyId}/users", companyId.toString())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andDo(print());
    }
}
