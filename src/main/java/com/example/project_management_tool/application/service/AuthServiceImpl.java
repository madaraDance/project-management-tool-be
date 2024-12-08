package com.example.project_management_tool.application.service;

import com.example.project_management_tool.application.dto.auth.LoginDTO;
import com.example.project_management_tool.application.dto.auth.SignupDTO;
import com.example.project_management_tool.application.dto.auth.SignupReadDTO;
import com.example.project_management_tool.application.dto.user.UserReadDTO;
import com.example.project_management_tool.domain.model.Company;
import com.example.project_management_tool.domain.model.User;
import com.example.project_management_tool.domain.repository.user_abstraction.IUserRepository;
import com.example.project_management_tool.domain.service.IAuthService;
import com.example.project_management_tool.domain.service.ICompanyService;
import com.example.project_management_tool.domain.service.IUserService;

import com.example.project_management_tool.infrastructure.initializer.Initializer;
import com.example.project_management_tool.presentation.config.JwtHelper;
import com.example.project_management_tool.presentation.shared.error.CustomResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {

    private final IUserService iUserService;

    private final ICompanyService iCompanyService;

    private final IUserRepository iUserRepository;

    private final JwtHelper jwtHelper;

    private final Initializer initializer;

    public  AuthServiceImpl (
            IUserService iUserService,
            ICompanyService iCompanyService,
            IUserRepository iUserRepository,
            JwtHelper jwtHelper,
            Initializer initializer
    ) {
        this.iUserService = iUserService;
        this.iCompanyService = iCompanyService;
        this.iUserRepository = iUserRepository;
        this.jwtHelper = jwtHelper;
        this.initializer = initializer;
    }

    @Transactional
    public SignupReadDTO signup(SignupDTO signupDTO) {
        Company savedCompany = iCompanyService.createCompany(signupDTO.getCompanyCreateDTO());

        UserReadDTO newUser = iUserService.createUser(savedCompany.getId(), signupDTO.getUserCreateDTO());

        initializer.initializeSystemRolesForCompany(savedCompany.getId());

        return new SignupReadDTO(newUser, savedCompany);

    }

    public String login(LoginDTO loginDTO) {
        User userFromDb = iUserRepository
                .findOneByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new CustomResourceNotFoundException("User with email: " + loginDTO.getEmail() + " was not found"));

        return jwtHelper.generateToken(userFromDb);
    }

}
