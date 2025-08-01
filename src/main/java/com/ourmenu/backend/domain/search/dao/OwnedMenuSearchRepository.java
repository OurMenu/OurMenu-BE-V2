package com.ourmenu.backend.domain.search.dao;

import com.ourmenu.backend.domain.search.domain.OwnedMenuSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OwnedMenuSearchRepository extends JpaRepository<OwnedMenuSearch, Long> {

    @Query("""
        SELECT oms
        FROM OwnedMenuSearch oms
        WHERE oms.id IN (
          SELECT MIN(o.id)
          FROM OwnedMenuSearch o
          WHERE o.userId = :userId
          GROUP BY o.menuTitle
        )
    """)
    Page<OwnedMenuSearch> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
