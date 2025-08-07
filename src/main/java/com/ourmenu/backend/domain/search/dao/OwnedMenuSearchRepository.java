package com.ourmenu.backend.domain.search.dao;

import com.ourmenu.backend.domain.search.domain.OwnedMenuSearch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import java.util.List;

public interface OwnedMenuSearchRepository extends JpaRepository<OwnedMenuSearch, Long> {

    Optional<OwnedMenuSearch> findByUserIdAndMenuId(Long userId, Long menuId);

    List<OwnedMenuSearch> findByUserId(Long userId, Pageable pageable);
}
