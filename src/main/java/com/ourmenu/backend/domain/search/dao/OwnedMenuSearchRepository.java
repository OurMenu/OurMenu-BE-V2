package com.ourmenu.backend.domain.search.dao;

import com.ourmenu.backend.domain.search.domain.OwnedMenuSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OwnedMenuSearchRepository extends JpaRepository<OwnedMenuSearch, Long> {

    @Query(value = "SELECT o FROM OwnedMenuSearch o " +
            "WHERE o.modifiedAt IN ( " +
            "    SELECT MAX(sub.modifiedAt) " +
            "    FROM OwnedMenuSearch sub " +
            "    WHERE sub.userId = :userId " +
            "    GROUP BY sub.menuTitle, sub.storeTitle " +
            ") " +
            "AND o.userId = :userId " +
            "ORDER BY o.modifiedAt DESC")
    Page<OwnedMenuSearch> findByUserIdOrderByModifiedAtDesc(Long userId, Pageable pageable);
}
