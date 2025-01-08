package com.ourmenu.backend.domain.search.dao;

import com.ourmenu.backend.domain.search.domain.NotOwnedMenuSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotOwnedMenuSearchRepository extends JpaRepository<NotOwnedMenuSearch,Long> {
}
