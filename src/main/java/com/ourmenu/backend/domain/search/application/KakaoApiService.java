package com.ourmenu.backend.domain.search.application;

import com.ourmenu.backend.domain.search.domain.NotFoundStore;
import com.ourmenu.backend.domain.search.dto.KaKaoMapDto;
import com.ourmenu.backend.domain.search.dto.KakaoAPIResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service

public class KakaoApiService {

    @Value("${kakao.APIKey}")
    private String kakaoAPIKey;
    private WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com/v2/local/search/keyword.json")
                .defaultHeader("Authorization", kakaoAPIKey)
                .build();
    }

    public NotFoundStore searchKakaoMap(String query) {
        KakaoAPIResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query)
                        .queryParam("size", 1)
                        .build()
                )
                .retrieve()
                .bodyToMono(KakaoAPIResponse.class)
                .block();
        KaKaoMapDto kaKaoMapDto = response.getDocuments()[0];
        return NotFoundStore.builder()
                .title(kaKaoMapDto.getPlaceName())
                .address(kaKaoMapDto.getAddressName())
                .mapX(kaKaoMapDto.getMapX())
                .mapY(kaKaoMapDto.getMapY())
                .storeId(kaKaoMapDto.getStoreId())
                .build();
    }
}
