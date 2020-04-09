package com.matekeszi.authentication.repository;

import com.matekeszi.authentication.domain.TokenEntity;
import com.matekeszi.authentication.domain.UserRoles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<TokenEntity, String> {
    TokenEntity findByToken(String token);
}
