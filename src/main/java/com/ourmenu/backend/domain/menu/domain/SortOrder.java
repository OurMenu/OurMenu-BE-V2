package com.ourmenu.backend.domain.menu.domain;

import lombok.Getter;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@Getter
public enum SortOrder {
    TITLE_ASC("이름순(가나다)", "title", Direction.ASC),
    TITLE_DESC("이름순(다나가)", "title", Direction.DESC),
    CREATED_AT_ASC("등록순(오래된순)", "createdAt", Direction.ASC),
    CREATED_AT_DESC("등록순(최신순)", "createdAt", Direction.DESC),
    PRICE_ASC("가격순(낮은순)", "price", Direction.ASC),
    PRICE_DESC("가격순(높은순)", "price", Direction.DESC);

    private final String description;
    private final String field;
    private final Direction direction;
    private final Sort sort;

    SortOrder(String description, String field, Direction direction) {
        this.description = description;
        this.field = field;
        this.direction = direction;
        this.sort = Sort.by(direction, field);
    }
}
