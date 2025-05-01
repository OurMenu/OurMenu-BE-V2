package com.ourmenu.backend.domain.home.config;

import com.ourmenu.backend.domain.home.data.HomeTestData;
import com.ourmenu.backend.domain.home.data.UserTestData;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class HomeTestConfig {

    @Autowired
    EntityManager entityManager;

    @Bean
    public UserTestData userTestData() {
        return new UserTestData(entityManager);
    }

    @Bean
    public HomeTestData homeTestData() {
        return new HomeTestData(entityManager);
    }
}
