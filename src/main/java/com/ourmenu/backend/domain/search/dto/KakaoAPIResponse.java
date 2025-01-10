package com.ourmenu.backend.domain.search.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class KakaoAPIResponse {
    private KaKaoMapDto[] documents;
}