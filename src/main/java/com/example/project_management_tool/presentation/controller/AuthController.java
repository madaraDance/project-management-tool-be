package com.example.project_management_tool.presentation.controller;

import com.example.project_management_tool.application.dto.auth.LoginDTO;
import com.example.project_management_tool.application.dto.auth.SignupDTO;
import com.example.project_management_tool.application.dto.auth.SignupReadDTO;
import com.example.project_management_tool.domain.service.IAuthService;
import com.example.project_management_tool.presentation.shared.GlobalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final IAuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController (
            IAuthService authService,
            AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<GlobalResponse<SignupReadDTO>> signup(@RequestBody @Valid SignupDTO signupDTO) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), authService.signup(signupDTO)), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<GlobalResponse<String>> login(@RequestBody @Valid LoginDTO loginDTO) {
        return new ResponseEntity<>(new GlobalResponse<>(HttpStatus.OK.value(), authService.login(loginDTO)), HttpStatus.OK);
    }
}
