package com.ourmenu.backend.domain.menu.dto;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import java.time.LocalDateTime;

@SqlResultSetMapping(
        name = "MenuSimpleDtoMapping",
        classes = @ConstructorResult(
                targetClass = MenuSimpleDto.class,
                columns = {
                        @ColumnResult(name = "menuId", type = Long.class),
                        @ColumnResult(name = "menuTitle", type = String.class),
                        @ColumnResult(name = "storeTitle", type = String.class),
                        @ColumnResult(name = "storeAddress", type = String.class),
                        @ColumnResult(name = "menuPrice", type = Integer.class),
                        @ColumnResult(name = "createdAt", type = LocalDateTime.class)
                }
        )
)
public interface MenuSimpleDto {

    Long getMenuId();

    String getMenuTitle();

    String getStoreTitle();

    String getStoreAddress();

    int getMenuPrice();

    LocalDateTime getCreatedAt();
}
