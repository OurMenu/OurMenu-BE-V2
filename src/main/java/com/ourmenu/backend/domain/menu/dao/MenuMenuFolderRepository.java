package com.ourmenu.backend.domain.menu.dao;

import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.menu.domain.MenuMenuFolder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuMenuFolderRepository extends JpaRepository<MenuMenuFolder, Long> {

    List<MenuMenuFolder> findAllByFolderId(Long FolderId);

    List<MenuMenuFolder> findAllByMenu(Menu menu);

    void deleteAllByMenu(Menu menu);

    void deleteByFolderId(Long folderId);
}
