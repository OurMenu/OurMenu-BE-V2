package com.ourmenu.backend.domain.home.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ourmenu.backend.domain.home.dto.GetRecommendMenu;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class StoreCacheService {

    private static final String STORE_CACHE_KEY_PREFIX = "GetRecommendMenu:";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void cacheStoreResponse(Long userId, List<GetRecommendMenu> getRecommendMenus) {
        String cacheKey = STORE_CACHE_KEY_PREFIX + userId;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(getRecommendMenus);
            redisTemplate.opsForValue().set(cacheKey, jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<GetRecommendMenu> getStoreResponse(Long userId) {
        String cacheKey = STORE_CACHE_KEY_PREFIX + userId;
        String jsonString = (String) redisTemplate.opsForValue().get(cacheKey);

        if (jsonString != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<GetRecommendMenu> getRecommendMenus = objectMapper.readValue(jsonString,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, GetRecommendMenu.class));
                return getRecommendMenus;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void deleteStoreResponse(Long userId) {
        String cacheKey = STORE_CACHE_KEY_PREFIX + userId;
        redisTemplate.delete(cacheKey);
    }
}