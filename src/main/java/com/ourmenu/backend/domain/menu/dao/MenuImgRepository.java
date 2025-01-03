package com.ourmenu.backend.domain.menu.dao;

import com.ourmenu.backend.domain.menu.domain.MenuImg;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuImgRepository extends JpaRepository<MenuImg, Long> {

    List<MenuImg> findAllByMenuId(Long menuId);

    void deleteAllByMenuId(Long menuId);
}
