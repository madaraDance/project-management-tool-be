package com.example.project_management_tool.application.service;

import com.example.project_management_tool.application.dto.user.UserCreateDTO;
import com.example.project_management_tool.application.dto.user.UserMapper;
import com.example.project_management_tool.application.dto.user.UserReadDTO;
import com.example.project_management_tool.application.dto.user.UserUpdateDTO;
import com.example.project_management_tool.domain.model.User;
import com.example.project_management_tool.domain.repository.company_abstraction.ICompanyRepository;
import com.example.project_management_tool.domain.repository.user_abstraction.IUserRepository;
import com.example.project_management_tool.domain.service.IUserService;
import com.example.project_management_tool.presentation.shared.error.CustomResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;
    private final IUserRepository iUserRepository;
    private final ICompanyRepository iCompanyRepository;


    public UserServiceImpl(UserMapper userMapper,
                           IUserRepository iUserRepository,
                           ICompanyRepository iCompanyRepository) {
        this.userMapper = userMapper;
        this.iUserRepository = iUserRepository;
        this.iCompanyRepository = iCompanyRepository;
    }

    @Override
    public List<UserReadDTO> getAllByCompanyId(UUID companyId) {
        List<UserReadDTO> userReadDTOs = new ArrayList<>();
        List<User> usersFromDb = iUserRepository.findAllByCompanyId(companyId);

        usersFromDb.forEach(user -> userReadDTOs.add(userMapper.userToUserReadDto(user)));

        return userReadDTOs;
    }

    @Override
    public UserReadDTO getUserById(UUID id) {
        User userFromDb = iUserRepository
                .findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("User with id: " + id + " was not found"));
        return userMapper.userToUserReadDto(userFromDb);
    }

    @Override
    public UserReadDTO createUser(UUID companyId ,UserCreateDTO userCreateDto) {
        iCompanyRepository
                .findOneById(companyId)
                .orElseThrow(() -> new CustomResourceNotFoundException("Company with id: " + companyId + " was not found"));

        User newUser = userMapper.userCreateDtoToUser(userCreateDto, companyId);
        User savedUser = iUserRepository.save(newUser);

        return userMapper.userToUserReadDto(savedUser);
    }

    @Override
    public UserReadDTO updateUser(UUID id, UserUpdateDTO userUpdateDtO) {
        User userFromDb = iUserRepository
                .findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("User with id: " + id + " was not found"));

        User userToUpdate = userMapper.userUpdateDtoToUser(userFromDb, userUpdateDtO);

        User savedUser = iUserRepository.save(userToUpdate);

        return userMapper.userToUserReadDto(savedUser);
    }

    @Override
    public void deleteUser(UUID id) {
        //TODO ensure that owner users can not be deleted.
        iUserRepository
                .findOneById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("User with id: " + id + " was not found"));

        iUserRepository.deleteById(id);
    }

}
