package com.matekeszi.authentication.repository;

import com.matekeszi.authentication.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findById(long userId);
}
