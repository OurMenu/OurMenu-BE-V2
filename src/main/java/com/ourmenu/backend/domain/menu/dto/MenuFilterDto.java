package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 메뉴판 - 메뉴 조회시 사용되는 필터 dto
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MenuFilterDto {

    private List<Tag> tags;
    private Long minPrice;
    private Long maxPrice;

    public static MenuFilterDto of(List<Tag> tags, Long minPrice, Long maxPrice) {
        return MenuFilterDto.builder()
                .tags(tags)
                .maxPrice(maxPrice)
                .maxPrice(maxPrice)
                .build();
    }
}
