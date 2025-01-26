package com.ourmenu.backend.domain.menu.dao;

import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.dto.MenuSimpleDto;
import com.ourmenu.backend.domain.store.domain.Store;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    boolean existsByStore(Store store);

    List<Menu> findMenusByUserId(Long userId);

    List<Menu> findMenusByStoreIdAndUserId(Long storeId, Long userId);

    boolean existsByUserIdAndId(Long userId, Long id);

    Menu findByIdAndUserId(Long menuId, Long userId);

    List<Menu> findByUserIdAndStoreId(Long userId, Long storeId);

    @Query(value = """
            SELECT m.id AS menuId, m.title AS menuTitle, s.title AS storeTitle, s.address
            AS storeAddress, m.price AS menuPrice, m.created_at AS createdAt

            FROM menu_menu_folder mf
            JOIN menu m ON mf.menu_id = m.id
            JOIN menu_tag mt ON mf.menu_id = mt.menu_id
            JOIN store s ON m.store_id = s.id
            WHERE mt.tag IN (:tags)
            AND m.price BETWEEN COALESCE(:minPrice, 0)
                         AND COALESCE(:maxPrice, 2147483647)
            AND m.user_id = :userId
            AND mf.folder_id = :menuFolderId
            GROUP BY m.id
            HAVING COUNT(DISTINCT mt.tag) = :tagSize
            """, nativeQuery = true)
    List<MenuSimpleDto> findByTagNameAndPriceRange(
            @Param("userId") Long userId,
            @Param("menuFolderId") Long menuFolderId,
            @Param("tags") List<String> tags,
            @Param("tagSize") Long tagSize,
            @Param("minPrice") Long minPrice,
            @Param("maxPrice") Long maxPrice
    );

    Page<Menu> findByUserId(Long userId, Pageable pageable);

    @Query(value = """
            SELECT DISTINCT m FROM Menu m
            JOIN FETCH m.store
            WHERE m.id = :menuId
            AND m.userId = :userId
            """)
    Optional<Menu> findByIdWithStore(Long userId, Long menuId);
}

