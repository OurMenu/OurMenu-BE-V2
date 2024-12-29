package com.ourmenu.backend.domain.menu.dao;

import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuFolderRepository extends JpaRepository<MenuFolder, Long> {

    @Query("SELECT COALESCE(MAX(m.index), 0) FROM MenuFolder m")
    int findMaxIndex();

    List<MenuFolder> findAllByUserId(Long userId);

    List<MenuFolder> findAllByUserIdOrderByIndexDesc(Long userId);

    @Query("SELECT m FROM MenuFolder m WHERE m.userId = :userId AND m.index BETWEEN :start AND :end")
    List<MenuFolder> findByUserIdAndIndexRange(@Param("userId") Long userId, @Param("start") int start,
                                               @Param("end") int end);

    @Modifying
    @Query("UPDATE MenuFolder m SET m.index = m.index + 1 " +
            "WHERE m.userId = :userId AND m.index BETWEEN :start AND :end")
    void incrementIndexes(@Param("userId") Long userId, @Param("start") int start, @Param("end") int end);

    @Modifying
    @Query("UPDATE MenuFolder m SET m.index = m.index - 1 " +
            "WHERE m.userId = :userId AND m.index BETWEEN :start AND :end")
    void decrementIndexes(@Param("userId") Long userId, @Param("start") int start, @Param("end") int end);

}
