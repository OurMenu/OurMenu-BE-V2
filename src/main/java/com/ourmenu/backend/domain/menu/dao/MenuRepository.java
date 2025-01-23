package com.ourmenu.backend.domain.menu.dao;

import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    boolean existsByStore(Store store);

    List<Menu> findMenusByUserId(Long userId);

    List<Menu> findMenusByStoreIdAndUserId(Long storeId, Long userId);

    boolean existsByUserIdAndId(Long userId, Long id);

    Menu findByIdAndUserId(Long menuId, Long userId);

    List<Menu> findByUserIdAndStoreId(Long userId, Long storeId);
}
