package com.matekeszi.authentication.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class TokenEntity {

    @Id
    private String token;
    private String username;
    private long expiration;
}
