package com.example.project_management_tool.integration;

import com.example.project_management_tool.application.dto.role.RoleCreateDTO;
import com.example.project_management_tool.data_factory.CompanyDataFactory;
import com.example.project_management_tool.data_factory.CompanyUserRoleDataFactory;
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


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

@AutoConfigureMockMvc
@SpringBootTest
public class RoleControllerTest extends AbstractIntegrationTest{

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @Autowired
    private CompanyDataFactory companyDataFactory;

    @Autowired
    private UserDataFactory userDataFactory;

    @Autowired
    private CompanyUserRoleDataFactory companyUserRoleDataFactory;

    @Autowired
    private IRoleRepository iRoleRepository;

    @Autowired
    private JwtHelper jwtHelper;

    private String token;

    private UUID companyId;

    private UUID roleId;

    @BeforeEach
    public void generateData() {

        Company company = companyDataFactory.createCompany("Company Name", true);

        companyId = company.getId();

        User user = userDataFactory.createUser("maksim@gmail.com", "securepass", companyId, true);

        CompanyUserRole cur = companyUserRoleDataFactory.createOwnerRoleWithPermissionsForUser(user.getId(), companyId);

        Role role = iRoleRepository.save(Role.builder().companyId(companyId).isSystemRole(false).name("BLYAAAAAA").build());

        roleId = role.getId();

        token = jwtHelper.generateToken(user, companyId);

    }

    @AfterEach
    public void cleanUpData() {
        companyDataFactory.deleteAll();
        userDataFactory.deleteAll();
        companyUserRoleDataFactory.deleteAll();
        iRoleRepository.deleteAll();
    }

    @Test
    public void testGetAllByCompanyId() throws Exception {
        mockMvc.perform(get("/companies/{companyId}/roles", companyId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andDo(print());
    }

    @Test
    public void testPost() throws Exception {
        String json = objectMapper.writeValueAsString(RoleCreateDTO.builder()
                    .name("CUSTOM_ROLE")
                    .build());

        mockMvc.perform(post("/companies/{companyId}/roles", companyId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.id").exists())
                .andDo(print());
    }

    @Test
    public void testPatch() throws Exception {

        String json = objectMapper.writeValueAsString(RoleCreateDTO.builder()
                .name("CUSTOM_ROLE")
                .build());

        mockMvc.perform(patch("/companies/{companyId}/roles/{roleId}", companyId.toString(), roleId.toString())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.name").value("CUSTOM_ROLE"))
                .andDo(print());
    }

    @Test
    public void testDelete() throws Exception {

        mockMvc.perform(delete("/companies/{companyId}/roles/{roleId}", companyId.toString(), roleId.toString())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").value("Role deleted."))
                .andDo(print());
    }


}
