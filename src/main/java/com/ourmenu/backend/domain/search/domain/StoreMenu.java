package com.ourmenu.backend.domain.search.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@Getter
@ToString
public class StoreMenu {

    @Field(name = "menuName")
    private String name;
    @Field(name = "menuPrice")
    private String price;
}
