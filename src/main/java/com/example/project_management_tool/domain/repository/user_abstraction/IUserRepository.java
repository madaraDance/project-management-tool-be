package com.example.project_management_tool.domain.repository.user_abstraction;

import com.example.project_management_tool.domain.model.User;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository {

    Optional<User> findOneById(UUID id);

    List<User> findAllByCompanyId(UUID companyId);

    User save(User user);

    void deleteById(UUID id);
}
