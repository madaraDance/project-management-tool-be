package com.example.project_management_tool.integration;

import com.example.project_management_tool.application.dto.user.UserCreateDTO;
import com.example.project_management_tool.application.dto.user.UserUpdateDTO;
import com.example.project_management_tool.data_factory.CompanyDataFactory;
import com.example.project_management_tool.data_factory.CompanyUserRoleDataFactory;
import com.example.project_management_tool.data_factory.UserDataFactory;
import com.example.project_management_tool.domain.model.*;
import com.example.project_management_tool.presentation.config.JwtHelper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.List;
import java.util.UUID;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest extends AbstractIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    CompanyDataFactory companyDataFactory;

    @Autowired
    UserDataFactory userDataFactory;

    @Autowired
    CompanyUserRoleDataFactory companyUserRoleDataFactory;

    private UUID companyId;

    private User user;

    private UUID userId;

    private String token;

    @BeforeEach
    public void generateData() {

        Company company = companyDataFactory.createCompany("Maksim Tech.", true);

        companyId = company.getId();

        user = userDataFactory.createUser("test@gmail.com", "securepass", companyId, true);

        userId = user.getId();

        CompanyUserRole cur = companyUserRoleDataFactory.createOwnerRoleWithPermissionsForUser(userId, companyId);

        token = jwtHelper.generateToken(user, companyId);

    }

    @AfterEach
    public void dataCleanUp() {
        companyUserRoleDataFactory.deleteAll();
        userDataFactory.deleteAll();
        companyDataFactory.deleteAll();
    }

    @Nested
    class getTests {
        @Test
        void testGetAllUsersFromCompany() throws Exception {
            mockMvc.perform(get("/companies/{companyId}/users", companyId.toString())
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath("$.data").isArray())
                    .andExpect(jsonPath("$.data", hasSize(1)))
                    .andDo(print());
        }

        @Test
        void testGetAllUsersFromCompanyUserDoesNotBelongTo() throws Exception {
            List<Company> companies = companyDataFactory.createCompaniesAndSave(1);
            UUID otherCompanyId = companies.getFirst().getId();

            mockMvc.perform(get("/companies/{companyId}/users", otherCompanyId.toString())
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden())
                    .andDo(print());
        }
    }

    @Nested
    class postTests {
        @Test
        void testCreateUserForCompany() throws Exception {
            User newUser = userDataFactory.createUser("email2@gmail.com", "securepass", companyId, false);
            String json = objectMapper.writeValueAsString(
                    UserCreateDTO.builder()
                      .firstName(newUser.getFirstName())
                      .lastName(newUser.getLastName())
                      .email(newUser.getEmail())
                      .password(newUser.getPassword())
                      .build());

            mockMvc.perform(post("/companies/{companyId}/users", companyId.toString())
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath("$.data.email").value(newUser.getEmail()))
                    .andDo(print());
        }

        @Test
        void testCreateUserForOtherCompany() throws Exception {
            Company otherCompany = companyDataFactory.createCompany("company", true);
            User newUser = userDataFactory.createUser("email2@gmail.com", "securepass", companyId, false);
            String json = objectMapper.writeValueAsString(
                    UserCreateDTO.builder()
                            .firstName(newUser.getFirstName())
                            .lastName(newUser.getLastName())
                            .email(newUser.getEmail())
                            .password(newUser.getPassword())
                            .build());
            mockMvc.perform(post("/companies/{companyId}/users", otherCompany.getId().toString())
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andExpect(status().isForbidden())
                    .andDo(print());
        }

    }

    @Nested
    class testPatch{

        @Test
        void testUpdateUser() throws Exception{
            String json = objectMapper.writeValueAsString(
                UserUpdateDTO.builder()
                        .firstName("Maksim")
                        .lastName("Minenko")
                        .password("verysecurepass")
                        .build()
            );

            mockMvc.perform(patch("/companies/{companyId}/users/{userId}", companyId.toString(), userId.toString())
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath("$.data.firstName").value("Maksim"))
                    .andExpect(jsonPath("$.data.lastName").value("Minenko"))
                    .andDo(print());
        }

        @Test
        void testUpdateUserWithoutNewData() throws Exception {
            String json = objectMapper.writeValueAsString(
                    UserUpdateDTO.builder()
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .password(user.getPassword())
                            .build()
            );

            mockMvc.perform(patch("/companies/{companyId}/users/{userId}", companyId.toString(), userId.toString())
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("error"))
                    .andExpect(jsonPath("$.errors.[0].message").value("No fields to update"))
                    .andDo(print());
        }
    }

    @Nested
    class testDelete {

        @Test
        void testDeleteUser() throws Exception {
            User u = userDataFactory.createUser("user@gmail.com", "password", companyId, true);

            mockMvc.perform(delete("/companies/{companyId}/users/{userId}", companyId.toString(), u.getId().toString())
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath("$.data").value("User deleted."))
                    .andDo(print());
        }

        @Test
        void deleteNonExistingUser() throws Exception {
            mockMvc.perform(delete("/companies/{companyId}/users/{userId}", companyId.toString(), UUID.randomUUID().toString())
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value("error"))
                    .andDo(print());
        }

        @Test
        void deleteUserFromAnotherCompany() throws Exception {
            Company company = companyDataFactory.createCompany("new company", true);
            User userFromOtherCompany = userDataFactory.createUsers(companyId, 1).stream().findFirst().orElse(null);

            mockMvc.perform(delete("/companies/{companyId}/users/{userId}", company.getId().toString(), userFromOtherCompany.getId().toString())
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isForbidden())
                    .andDo(print());
        }
    }



}
