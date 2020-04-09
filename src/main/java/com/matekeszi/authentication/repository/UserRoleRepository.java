package com.matekeszi.authentication.repository;

import com.matekeszi.authentication.domain.UserRoles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRoles, Long> {
}
