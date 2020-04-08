package com.matekeszi.authentication.repository;

import com.matekeszi.authentication.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
