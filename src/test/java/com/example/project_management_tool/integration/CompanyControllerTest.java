package com.example.project_management_tool.integration;

import com.example.project_management_tool.application.dto.company.CompanyUpdateDTO;
import com.example.project_management_tool.data_factory.CompanyDataFactory;
import com.example.project_management_tool.data_factory.CompanyUserRoleDataFactory;
import com.example.project_management_tool.data_factory.UserDataFactory;
import com.example.project_management_tool.domain.model.Company;
import com.example.project_management_tool.domain.model.User;
import com.example.project_management_tool.domain.model.CompanyUserRole;
import com.example.project_management_tool.presentation.config.JwtHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CompanyDataFactory companyDataFactory;

    @Autowired
    private UserDataFactory userDataFactory;

    @Autowired
    private CompanyUserRoleDataFactory companyUserRoleDataFactory;

    @Autowired
    private JwtHelper jwtHelper;

    private String token;

    private UUID companyId;

    @BeforeEach
    public void generateData() {

        Company company = companyDataFactory.createCompany("Company Name", true);

        companyId = company.getId();

        User user = userDataFactory.createUser("maksim@gmail.com", "securepass", companyId, true);

        CompanyUserRole cur = companyUserRoleDataFactory.createOwnerRoleWithPermissionsForUser(user.getId(), companyId);

        token = jwtHelper.generateToken(user, companyId);

    }

    @AfterEach
    public void cleanUpData() {
        companyDataFactory.deleteAll();
        userDataFactory.deleteAll();
        companyUserRoleDataFactory.deleteAll();
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get("/companies/{id}", companyId)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.id").value(companyId.toString()))
                .andDo(print());
    }

    @Test
    void testPatch() throws Exception {
        String json = objectMapper.writeValueAsString(CompanyUpdateDTO.builder()
                        .companyName("updated company name")
                        .description("new description")
                .build());

        mockMvc.perform(patch("/companies/{id}", companyId)
                .header("Authorization", "Bearer " +token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data.companyName").value("updated company name"))
                .andExpect(jsonPath("$.data.description").value("new description"))
                .andDo(print());

    }

    @Test
    void testGetOtherCompany() throws Exception {
        Company c = companyDataFactory.createCompany("other company", true);

        mockMvc.perform(get("/companies/{id}", c.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

}
