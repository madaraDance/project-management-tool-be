package com.example.project_management_tool.domain.service;

import com.example.project_management_tool.application.dto.user.UserCreateDTO;
import com.example.project_management_tool.application.dto.user.UserReadDTO;
import com.example.project_management_tool.application.dto.user.UserUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    List<UserReadDTO> getAllByCompanyId(UUID companyId);

    UserReadDTO getUserById(UUID id);

    UserReadDTO createUser(UUID companyId,UserCreateDTO userCreateDto);

    UserReadDTO updateUser(UUID id, UserUpdateDTO userUpdateDto);

    void deleteUser(UUID id);
}
