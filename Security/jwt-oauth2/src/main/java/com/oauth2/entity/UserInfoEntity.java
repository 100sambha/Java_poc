package com.oauth2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER_INFO")
public class UserInfoEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USER_NAME", unique = true)
    private String userName;

    @Column(name = "EMAIL_ID", nullable = false, unique = true)
    private String emailId;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "MOBILE_NUMBER")
    private String mobileNumber;

    @Column(name = "Roles", nullable = false)
    private String roles;                           //ROLE_ADMIN, ROLE_MANAGER, ROLE_USER

    @OneToMany(mappedBy = "userInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RefreshTokenEntity> refreshTokens;
}