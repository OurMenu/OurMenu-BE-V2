package com.ourmenu.backend.domain.search.domain;

import jakarta.persistence.Id;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "store")
@Getter
@ToString
public class SearchableStore {

    @Id
    private String id;
    @Field(name = "name")
    private String title;
    private String address;
    @Field(name = "images")
    private List<String> storeImgs;
    @Field(name = "menus")
    private List<StoreMenu> storeMenus;
    private String storeId;
}
