package com.ourmenu.backend.domain.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class KaKaoMapDto {

    @JsonProperty("address_name")
    private String addressName;

    @JsonProperty("place_name")
    private String placeName;

    @JsonProperty("x")
    private Double mapX;

    @JsonProperty("y")
    private Double mapY;

    @JsonProperty("id")
    private String storeId;
}