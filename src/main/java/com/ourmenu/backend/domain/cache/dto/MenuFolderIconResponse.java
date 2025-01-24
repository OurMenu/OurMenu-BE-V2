package com.ourmenu.backend.domain.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class MenuFolderIconResponse {

    private String name;
    private String data;

    public static MenuFolderIconResponse of(String name, String data) {
        return MenuFolderIconResponse.builder()
                .name(name)
                .data(data)
                .build();
    }
}
