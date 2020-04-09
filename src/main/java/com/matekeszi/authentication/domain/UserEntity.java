package com.matekeszi.authentication.domain;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private boolean active;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private boolean deletedFlag;
    private String email;
    private String emailToken;
    private LocalDateTime lastLogin;
    private String name;
    private boolean nextLoginChangePwd;
    private String password;
    private boolean passwordExpired;
    private String phone;
    private long settlementId;
    private String tempPassword;
    private boolean tempPasswordExpired;
    private LocalDateTime updatedAt;
    private String username;
    private String userType;
    private String settlementsBySettlementId;
    private String userByCreatedId;
    private String userByDeletedId;
    private String userByUpdatedId;

    @OneToMany(mappedBy = "user")
    private List<UserRoles> roles;
}
