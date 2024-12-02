package com.ourmenu.backend.domain.menu.dao;

import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MenuFolderRepository extends JpaRepository<MenuFolder, Long> {

    @Query("SELECT COALESCE(MAX(m.index), 0) FROM MenuFolder m")
    int findMaxIndex();
}
