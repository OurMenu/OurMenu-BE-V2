package com.ourmenu.backend.domain.tag.dao;

import com.ourmenu.backend.domain.tag.domain.MenuTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuTagRepository extends JpaRepository<MenuTag, Long> {

    void deleteAllByMenuId(Long menuId);

    List<MenuTag> findMenuTagsByMenuId(Long menuId);
}
