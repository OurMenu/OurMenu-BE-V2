package com.ourmenu.backend.domain.home.dao;

import com.ourmenu.backend.domain.home.domain.HomeQuestionAnswer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeQuestionAnswerRepository extends JpaRepository<HomeQuestionAnswer, Long> {

    Optional<HomeQuestionAnswer> findByUserId(Long userId);
}
