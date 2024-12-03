package com.example.project_management_tool.infrastructure;

import com.example.project_management_tool.domain.repository.user_abstraction.IUserRepository;
import com.example.project_management_tool.domain.repository.user_abstraction.IUserRepositoryJpa;
import com.example.project_management_tool.domain.model.User;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    IUserRepositoryJpa iUserRepositoryJPA;

    public UserRepositoryImpl(IUserRepositoryJpa iUserRepositoryJPA) {
        this.iUserRepositoryJPA = iUserRepositoryJPA;
    }

    @Override
    public Optional<User> findOneById(UUID id) {
        return iUserRepositoryJPA.findById(id);
    }

    @Override
    public List<User> findAllByCompanyId(UUID companyId) {
        return iUserRepositoryJPA.findAllByCompanyId(companyId);
    }

    @Override
    public User save(User user) {
        return iUserRepositoryJPA.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        iUserRepositoryJPA.deleteById(id);
    }
}
