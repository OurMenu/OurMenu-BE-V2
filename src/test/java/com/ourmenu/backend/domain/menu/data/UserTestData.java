package com.ourmenu.backend.domain.menu.data;

import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.domain.user.domain.SignInType;
import com.ourmenu.backend.domain.user.domain.User;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

public class UserTestData {

    private EntityManager entityManager;

    public UserTestData(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional
    public CustomUserDetails createTestEmailUser() {
        String email = "testEmailUser@naver.com";
        String password = "password1!";
        User user = User.builder()
                .email(email)
                .password(password)
                .signInType(SignInType.EMAIL)
                .build();
        entityManager.persist(user);
        entityManager.flush();
        return new CustomUserDetails(user.getId(), email, password);
    }

    @Transactional
    public CustomUserDetails createTestKakaoUser() {
        String email = "testKakaoUser@naver.com";
        String password = "password1!";
        User user = User.builder()
                .email(email)
                .password(password)
                .signInType(SignInType.KAKAO)
                .build();
        entityManager.persist(user);
        entityManager.flush();
        return new CustomUserDetails(user.getId(), email, password);
    }
}
