package com.ourmenu.backend.domain.user.domain;

import com.ourmenu.backend.global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    private SignInType signInType;

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
