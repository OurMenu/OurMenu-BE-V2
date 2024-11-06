package com.ourmenu.backend.domain.user.dao;

import com.ourmenu.backend.domain.user.domain.RedisToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RedisRepository extends JpaRepository<RedisToken, Long> {

    Optional<RedisToken> findByAccessToken(String accessToken);
}
