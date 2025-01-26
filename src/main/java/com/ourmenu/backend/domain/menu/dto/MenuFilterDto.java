package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.menu.domain.SortOrder;
import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
    private Pageable pageable;
    private SortOrder sortOrder;


    public static MenuFilterDto of(List<Tag> tags, Long minPrice, Long maxPrice) {
        return MenuFilterDto.builder()
                .tags(tags)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();
    }

    public static MenuFilterDto of(int page, int size, SortOrder sortOrder) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder.getDirection(), sortOrder.getField()));
        return MenuFilterDto.builder()
                .pageable(pageable)
                .sortOrder(sortOrder)
                .build();
    }
}
