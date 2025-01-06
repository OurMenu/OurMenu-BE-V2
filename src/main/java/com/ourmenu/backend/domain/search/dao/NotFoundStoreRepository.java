package com.ourmenu.backend.domain.search.dao;

import com.ourmenu.backend.domain.search.domain.NotFoundStore;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotFoundStoreRepository extends JpaRepository<NotFoundStore, Long> {

    List<NotFoundStore> findByTitleContaining(String title);
}
