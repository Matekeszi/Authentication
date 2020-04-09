package com.matekeszi.authentication.service;

import com.matekeszi.authentication.domain.Roles;
import com.matekeszi.authentication.domain.UserEntity;
import com.matekeszi.authentication.domain.UserRoles;
import com.matekeszi.authentication.repository.UserRepository;
import com.matekeszi.authentication.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserEntity findById(final long userId) {
        return userRepository.findById(userId);
    }

    public void register(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    public Iterable<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(long userId) {
        userRepository.deleteById(userId);
    }
}
