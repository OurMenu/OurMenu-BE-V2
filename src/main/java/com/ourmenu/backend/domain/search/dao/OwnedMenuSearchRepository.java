package com.ourmenu.backend.domain.search.dao;

import com.ourmenu.backend.domain.search.domain.OwnedMenuSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OwnedMenuSearchRepository extends JpaRepository<OwnedMenuSearch, Long> {

    @Query("""
        SELECT oms
        FROM OwnedMenuSearch oms
        WHERE oms.userId = :userId
    """)
    Page<OwnedMenuSearch> findByUserId(@Param("userId") Long userId, Pageable pageable);

    Optional<OwnedMenuSearch> findByUserIdAndMenuId(Long userId, Long menuId);
}
