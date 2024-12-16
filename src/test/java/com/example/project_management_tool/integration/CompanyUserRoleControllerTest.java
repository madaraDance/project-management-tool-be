package com.example.project_management_tool.integration;

import com.example.project_management_tool.application.dto.company_user_role.CompanyUserRoleCreateDTO;
import com.example.project_management_tool.data_factory.CompanyDataFactory;
import com.example.project_management_tool.data_factory.CompanyUserRoleDataFactory;
import com.example.project_management_tool.data_factory.RoleDataFactory;
import com.example.project_management_tool.data_factory.UserDataFactory;
import com.example.project_management_tool.domain.model.Company;
import com.example.project_management_tool.domain.model.CompanyUserRole;
import com.example.project_management_tool.domain.model.Role;
import com.example.project_management_tool.domain.model.User;
import com.example.project_management_tool.domain.repository.role_abstraction.IRoleRepository;
import com.example.project_management_tool.presentation.config.JwtHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;


@SpringBootTest
@AutoConfigureMockMvc
public class CompanyUserRoleControllerTest extends AbstractIntegrationTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CompanyDataFactory companyDataFactory;

    @Autowired
    private UserDataFactory userDataFactory;

    @Autowired
    private CompanyUserRoleDataFactory companyUserRoleDataFactory;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private RoleDataFactory roleDataFactory;

    private String token;

    private UUID companyId;

    private UUID userId;

    private UUID curId;

    @BeforeEach
    public void generateData() {

        Company company = companyDataFactory.createCompany("Company Name", true);

        companyId = company.getId();

        User user = userDataFactory.createUser("maksim@gmail.com", "securepass", companyId, true);

        userId = user.getId();

        CompanyUserRole cur = companyUserRoleDataFactory.createOwnerRoleWithPermissionsForUser(user.getId(), companyId);

        curId = cur.getId();

        token = jwtHelper.generateToken(user, companyId);

    }

    @AfterEach
    public void cleanUpData() {
        companyDataFactory.deleteAll();
        userDataFactory.deleteAll();
        companyUserRoleDataFactory.deleteAll();
    }

    @Test
    public void testGetAllCompanyManagers() throws  Exception{
        mockMvc.perform(get("/companies/{companyId}/managers", companyId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andDo(print());
    }

    @Test
    public void testCreateCompanyManager() throws Exception {
       Role role = roleDataFactory.createRole("MANAGER_ROLE", companyId, true);
       User user2 = userDataFactory.createUser("email@gmail.com", "securepass", companyId, true);
        String json = objectMapper.writeValueAsString(
                CompanyUserRoleCreateDTO.builder()
                        .userId(user2.getId())
                        .roleId(role.getId())
                        .build()
        );

        mockMvc.perform(post("/companies/{companyId}/managers", companyId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.id").exists())
                .andDo(print());
    }
    @Test
    public void testUpdateCompanyManager() throws Exception {
        Role role = roleDataFactory.createRole("MANAGER_ROLE", companyId, true);

        String json = objectMapper.writeValueAsString(
                CompanyUserRoleCreateDTO.builder()
                        .userId(userId)
                        .roleId(role.getId())
                        .build()
        );

        mockMvc.perform(patch("/companies/{companyId}/managers/{id}", companyId, curId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.roleName").value(role.getName()))
                .andDo(print());
    }

    @Test
    public void testDeleteCompanyManager() throws Exception {
        Role role = roleDataFactory.createRole("MANAGER_ROLE", companyId, true);
        User user2 = userDataFactory.createUser("email@gmail.com", "securepass", companyId, true);

        CompanyUserRole cur2 = companyUserRoleDataFactory.createCompanyUserRole(user2.getId(), companyId, role.getId(), true);

        mockMvc.perform(delete("/companies/{companyId}/managers/{id}", companyId, cur2.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").value("Company manager was deleted."))
                .andDo(print());
    }

}
