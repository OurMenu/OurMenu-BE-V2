package com.ourmenu.backend.domain.home.application;

import com.ourmenu.backend.domain.home.dto.GetRecommendMenu;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class StoreCacheService {

    private static final String STORE_CACHE_KEY_PREFIX = "GetRecommendMenu:";  // "store:{userId}" 형태로 저장

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // GetStoreResponse 객체를 캐시 저장
    public void cacheStoreResponse(Long userId, List<GetRecommendMenu> getRecommendMenus) {
        String cacheKey = STORE_CACHE_KEY_PREFIX + userId;
        redisTemplate.opsForValue().set(cacheKey, getRecommendMenus);
    }

    // 캐시에서 GetStoreResponse 객체 조회
    public List<GetRecommendMenu> getStoreResponse(Long userId) {
        String cacheKey = STORE_CACHE_KEY_PREFIX + userId;
        return (List<GetRecommendMenu>) redisTemplate.opsForValue().get(cacheKey);
    }

    // 캐시에서 데이터 삭제
    public void deleteStoreResponse(Long userId) {
        String cacheKey = STORE_CACHE_KEY_PREFIX + userId;
        redisTemplate.delete(cacheKey);
    }
}