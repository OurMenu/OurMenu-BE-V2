package com.ourmenu.backend.domain.search.data;

import jakarta.persistence.EntityManager;

public class SearchTestData {

    private EntityManager entityManager;

    public SearchTestData(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
