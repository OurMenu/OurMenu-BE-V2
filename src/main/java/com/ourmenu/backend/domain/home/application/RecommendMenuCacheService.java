package com.ourmenu.backend.domain.home.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ourmenu.backend.domain.home.dto.GetRecommendMenuResponse;
import com.ourmenu.backend.domain.home.exception.DeserializeFailureException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RecommendMenuCacheService {

    private static final String STORE_CACHE_KEY_PREFIX = "GetRecommendMenuResponse:";
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 추천 메뉴 캐시 값을 저장한다.
     *
     * @param userId
     * @param getRecommendMenuResponses
     */
    public void cacheStoreResponse(Long userId, List<GetRecommendMenuResponse> getRecommendMenuResponses,
                                   long ttlMinutes) {
        String cacheKey = STORE_CACHE_KEY_PREFIX + userId;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonString = objectMapper.writeValueAsString(getRecommendMenuResponses);
            redisTemplate.opsForValue().set(cacheKey, jsonString, ttlMinutes, TimeUnit.MINUTES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 추천 메뉴 캐시 값을 조회한다.
     *
     * @param userId
     * @return
     */
    public List<GetRecommendMenuResponse> getStoreResponse(Long userId) {
        String cacheKey = STORE_CACHE_KEY_PREFIX + userId;
        String jsonString = (String) redisTemplate.opsForValue().get(cacheKey);

        if (jsonString != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                List<GetRecommendMenuResponse> getRecommendMenuResponses = objectMapper.readValue(jsonString,
                        objectMapper.getTypeFactory()
                                .constructCollectionType(List.class, GetRecommendMenuResponse.class));
                return getRecommendMenuResponses;
            } catch (IOException e) {
                throw new DeserializeFailureException();
            }
        }
        return null;
    }

    public void deleteStoreResponse(Long userId) {
        String cacheKey = STORE_CACHE_KEY_PREFIX + userId;
        redisTemplate.delete(cacheKey);
    }
}