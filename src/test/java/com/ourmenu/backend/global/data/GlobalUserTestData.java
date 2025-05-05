package com.ourmenu.backend.global.data;

import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.domain.user.domain.MealTime;
import com.ourmenu.backend.domain.user.domain.SignInType;
import com.ourmenu.backend.domain.user.domain.User;
import jakarta.persistence.EntityManager;
import java.time.LocalTime;
import org.springframework.transaction.annotation.Transactional;

public class GlobalUserTestData {

    private EntityManager entityManager;

    public GlobalUserTestData(EntityManager entityManager) {
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

    @Transactional
    public CustomUserDetails createTestEmailUserWithMealTime() {
        CustomUserDetails testEmailUser = createTestEmailUser();

        MealTime mealTime = MealTime.builder()
                .userId(testEmailUser.getId())
                .mealTime(LocalTime.NOON)
                .build();
        entityManager.persist(mealTime);

        return testEmailUser;
    }
}
