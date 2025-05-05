package com.ourmenu.backend.domain.menu.config;

import com.ourmenu.backend.domain.menu.data.MenuTestData;
import com.ourmenu.backend.domain.menu.data.StoreTestData;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MenuTestConfig {

    @Autowired
    EntityManager entityManager;

    @Bean
    public MenuTestData menuTestData() {
        return new MenuTestData(entityManager, storeTestData());
    }

    @Bean
    public StoreTestData storeTestData() {
        return new StoreTestData(entityManager);
    }
}
