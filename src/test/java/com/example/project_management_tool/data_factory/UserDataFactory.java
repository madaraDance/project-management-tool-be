package com.example.project_management_tool.data_factory;

import com.example.project_management_tool.domain.repository.user_abstraction.IUserRepository;
import com.example.project_management_tool.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class UserDataFactory {

    @Autowired
    public IUserRepository iUserRepository;

    public User createUser(String email, String password, UUID companyId, boolean doSave) {
        User u = User.builder()
                .firstName("User first name")
                .lastName("User last name")
                .email(email)
                .password(password)
                .companyId(companyId)
                .build();

        if (doSave) {
            return iUserRepository.save(u);
        }
        return u;
    }

    public List<User> createUsers(UUID companyId, int count) {
        List<User> users = new ArrayList<>();

        for (int i = 0; i <count; i++) {
            users.add(
                    User
                            .builder()
                            .firstName("User first name")
                            .lastName("User last name")
                            .email("email" + i + "@gmail.com")
                            .password("password")
                            .companyId(companyId)
                            .build()
            );
        }

        return iUserRepository.saveAll(users);
    }

    public void deleteAll() {
        iUserRepository.deleteAll();
    }
}
