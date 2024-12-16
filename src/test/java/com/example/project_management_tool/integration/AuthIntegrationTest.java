package com.example.project_management_tool.integration;

import com.example.project_management_tool.application.dto.auth.LoginDTO;
import com.example.project_management_tool.application.dto.auth.SignupDTO;
import com.example.project_management_tool.application.dto.company.CompanyCreateDTO;
import com.example.project_management_tool.application.dto.user.UserCreateDTO;
import com.example.project_management_tool.data_factory.CompanyDataFactory;
import com.example.project_management_tool.data_factory.UserDataFactory;
import com.example.project_management_tool.domain.repository.company_user_role_abstraction.ICompanyUserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.project_management_tool.domain.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class AuthIntegrationTest extends AbstractIntegrationTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDataFactory userDataFactory;

    @Autowired
    private CompanyDataFactory companyDataFactory;

    @Autowired
    private ICompanyUserRoleRepository iCompanyUserRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private String token;



    @AfterEach
    public void dataCleanUp() {
        userDataFactory.deleteAll();
        companyDataFactory.deleteAll();
    }

    @Test
    public void testSignUp() throws Exception{
        CompanyCreateDTO companyDto = CompanyCreateDTO.builder()
                .companyName("Maksim Tech")
                .description("Maksim's company")
                .build();
        UserCreateDTO userDto = UserCreateDTO.builder()
                .firstName("Maksim")
                .lastName("Minenko")
                .email("random@gmail.com")
                .password("securepass")
                .build();
        String json = objectMapper.writeValueAsString(
                SignupDTO.builder()
                        .companyCreateDTO(companyDto)
                        .userCreateDTO(userDto)
                        .build()
        );

        mockMvc.perform(post("/auth/signup")
               .contentType(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.status").value("success"))
               .andExpect(jsonPath("$.data.userReadDTO.id").exists())
               .andExpect(jsonPath("$.data.company.id").exists())
               .andDo(print());

    }

    @Test
    public void testLogin() throws Exception{
        Company company = companyDataFactory.createCompany("company", true);

        User u = userDataFactory.createUser("maksim@gmail.com", passwordEncoder.encode("securepass"), company.getId(), true);

        String json = objectMapper.writeValueAsString(LoginDTO.builder()
                        .email(u.getEmail())
                        .password("securepass")
                        .build());

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());
    }

}
