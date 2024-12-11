package com.example.project_management_tool.domain.repository.user_abstraction;

import com.example.project_management_tool.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface IUserRepositoryJpa extends JpaRepository<User, UUID> {

    @Nullable
    Optional<User> findById(UUID id);

    List<User> findAllByCompanyId(UUID companyId);

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndCompanyId(String email, UUID companyId);

}
