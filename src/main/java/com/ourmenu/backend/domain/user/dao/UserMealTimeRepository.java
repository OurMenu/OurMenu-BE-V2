package com.ourmenu.backend.domain.user.dao;

import com.ourmenu.backend.domain.user.domain.UserMealTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMealTimeRepository extends JpaRepository<UserMealTime, String> {
}
