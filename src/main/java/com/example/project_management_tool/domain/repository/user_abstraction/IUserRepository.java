package com.example.project_management_tool.domain.repository.user_abstraction;

import com.example.project_management_tool.domain.model.User;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {

    Optional<User> findOneById(UUID id);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByEmailAndCompanyId(String email, UUID companyId);

    List<User> findAllByCompanyId(UUID companyId);

    User save(User user);

    List<User> saveAll(List<User> users);

    void deleteById(UUID id);

    void deleteAll();
}
