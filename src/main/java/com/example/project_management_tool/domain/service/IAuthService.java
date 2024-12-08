package com.example.project_management_tool.domain.service;

import com.example.project_management_tool.application.dto.auth.LoginDTO;
import com.example.project_management_tool.application.dto.auth.SignupDTO;
import com.example.project_management_tool.application.dto.auth.SignupReadDTO;

public interface IAuthService {

    SignupReadDTO signup(SignupDTO signupDTO);

    String login(LoginDTO loginDTO);
}
