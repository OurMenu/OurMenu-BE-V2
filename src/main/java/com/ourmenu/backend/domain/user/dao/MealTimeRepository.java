package com.ourmenu.backend.domain.user.dao;

import com.ourmenu.backend.domain.user.domain.MealTime;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MealTimeRepository extends JpaRepository<MealTime, Long> {
    void deleteAllByUserId(Long userId);
}
