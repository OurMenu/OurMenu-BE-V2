package com.ourmenu.backend.global;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

public class DatabaseCleaner implements InitializingBean {

    private final List<String> tables = new ArrayList<>();

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void findDatabaseTableNames() {
        List<String> tableInfos = entityManager.createNativeQuery("SHOW TABLES").getResultList();
        tables.addAll(tableInfos);
    }

    private void truncate() {
        entityManager.createNativeQuery(String.format("SET FOREIGN_KEY_CHECKS = %d", 0)).executeUpdate();
        for (String tableName : tables) {
            entityManager.createNativeQuery(String.format("TRUNCATE TABLE %s", tableName)).executeUpdate();
        }
        entityManager.createNativeQuery(String.format("SET FOREIGN_KEY_CHECKS = %d", 1)).executeUpdate();
    }

    @Override
    public void afterPropertiesSet() {
        findDatabaseTableNames();
    }

    @Transactional
    public void clear() {
        entityManager.clear();
        truncate();
    }
}