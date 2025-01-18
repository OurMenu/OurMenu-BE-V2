package com.ourmenu.backend.domain.search.dao;

import com.ourmenu.backend.domain.search.domain.OwnedMenuSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnedMenuSearchRepository extends JpaRepository<OwnedMenuSearch, Long> {

    Page<OwnedMenuSearch> findByUserIdOrderBySearchAtDesc(Long userId, Pageable pageable);
}
