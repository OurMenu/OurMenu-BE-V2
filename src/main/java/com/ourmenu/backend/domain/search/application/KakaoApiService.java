package com.ourmenu.backend.domain.search.application;

import com.ourmenu.backend.domain.search.domain.NotFoundStore;
import com.ourmenu.backend.domain.search.dto.KaKaoMapDto;
import com.ourmenu.backend.domain.search.dto.KakaoAPIResponse;
import com.ourmenu.backend.domain.search.exception.ExceededDailyQuotaException;
import com.ourmenu.backend.domain.search.exception.NotFoundStoreInKakaoException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
        KakaoAPIResponse response = getKakaoAPI(query);

        if (response.getDocuments().length == 0) {
            throw new NotFoundStoreInKakaoException();
        }
        KaKaoMapDto kaKaoMapDto = response.getDocuments()[0];
        return NotFoundStore.builder()
                .title(kaKaoMapDto.getPlaceName())
                .address(kaKaoMapDto.getAddressName())
                .mapX(kaKaoMapDto.getMapX())
                .mapY(kaKaoMapDto.getMapY())
                .storeId(kaKaoMapDto.getStoreId())
                .build();
    }

    private KakaoAPIResponse getKakaoAPI(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("query", query)
                        .queryParam("size", 1)
                        .build()
                )
                .retrieve()
                .onStatus(
                        //http code 확인(일일 사용량 초과시 예외)
                        HttpStatus.UNAUTHORIZED::equals,
                        kakaoResponse -> kakaoResponse.bodyToMono(String.class)
                                .map(errorBody -> new ExceededDailyQuotaException())
                )
                .bodyToMono(KakaoAPIResponse.class)
                .block();
    }
}
