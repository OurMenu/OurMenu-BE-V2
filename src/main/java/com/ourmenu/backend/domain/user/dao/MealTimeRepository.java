package com.ourmenu.backend.domain.user.dao;

import com.ourmenu.backend.domain.user.domain.MealTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MealTimeRepository extends JpaRepository<MealTime, Long> {
    void deleteAllByUserId(Long userId);

    List<MealTime> findAllByUserId(Long userId);
}
