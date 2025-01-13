package com.ourmenu.backend.domain.menu.dao;

import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    boolean existsByStore(Store store);

    boolean existsByUserIdAndId(Long userId, Long id);
}
