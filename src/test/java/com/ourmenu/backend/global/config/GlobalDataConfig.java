package com.ourmenu.backend.global.config;

import com.ourmenu.backend.global.data.GlobalUserTestData;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class GlobalDataConfig {

    @Autowired
    EntityManager entityManager;

    @Bean
    public GlobalUserTestData globalUserTestData() {
        return new GlobalUserTestData(entityManager);
    }
}
