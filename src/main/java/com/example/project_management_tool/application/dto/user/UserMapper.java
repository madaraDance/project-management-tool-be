package com.example.project_management_tool.application.dto.user;

import com.example.project_management_tool.domain.model.User;
import com.example.project_management_tool.presentation.shared.error.CustomNoFieldsToUpdateException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {
    public User userCreateDtoToUser(UserCreateDTO dto, UUID companyId) {
        return User
                .builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail().toLowerCase())
                .password(dto.getPassword())
                .companyId(companyId)
                .build();
    }

    public User userUpdateDtoToUser(User userFromDb, UserUpdateDTO dto) {
        User userToSave = User.builder()
                .id(userFromDb.getId())
                .firstName(dto.getFirstName() != null && !dto.getFirstName().isBlank()
                        ? dto.getFirstName()
                        : userFromDb.getFirstName())
                .lastName(dto.getLastName() != null && !dto.getLastName().isBlank()
                        ? dto.getLastName()
                        : userFromDb.getLastName())
                .email(userFromDb.getEmail())
                .password(dto.getPassword() != null && !dto.getPassword().isBlank()
                        ? dto.getPassword()
                        : userFromDb.getPassword())
                .companyId(userFromDb.getCompanyId())
                .createdAt(userFromDb.getCreatedAt())
                .updatedAt(userFromDb.getUpdatedAt())
                .build();

        if (hasSameFields(userFromDb, userToSave)) {
            throw new CustomNoFieldsToUpdateException("No fields to update");
        }

        return userToSave;
    }

    public UserReadDTO userToUserReadDto(User user){
        return UserReadDTO
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .companyId(user.getCompanyId())
                .build();
    }

    public boolean hasSameFields(User userFromDb, User userToSave) {

        return userFromDb.getFirstName().equals(userToSave.getFirstName())
                && userFromDb.getLastName().equals(userToSave.getLastName())
                && userFromDb.getPassword().equals(userToSave.getPassword());

    }
}
