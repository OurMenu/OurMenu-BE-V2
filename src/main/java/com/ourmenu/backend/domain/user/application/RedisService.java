package com.ourmenu.backend.domain.user.application;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Refresh Token 저장
    public void saveRefreshToken(Long userId, String refreshToken, long expirationTime) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("refreshToken:" + userId, refreshToken, Duration.ofMillis(expirationTime));
    }

    // Access Token 저장
    public void saveAccessToken(Long userId, String accessToken, long expirationTime) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("accessToken:" + userId, accessToken, Duration.ofMillis(expirationTime));
    }

    // Refresh Token 조회
    public String getRefreshToken(Long userId) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get("refreshToken:" + userId);
    }

    // Refresh Token 조회
    public String getAccessToken(Long userId) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get("accessToken:" + userId);
    }

    // Refresh Token 삭제
    public void deleteRefreshToken(Long userId) {
        redisTemplate.delete("refreshToken:" + userId);
    }

    // Refresh Token 삭제
    public void deleteAccessToken(Long userId) {
        redisTemplate.delete("accessToken:" + userId);
    }

}
