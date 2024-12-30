package com.ourmenu.backend.domain.search.domain;

import com.ourmenu.backend.domain.menu.domain.Menu;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class SearchableStore {

    @Id
    private String id;

    private String name;

    private List<String> images;

    @Column(name = "menus")
    private List<Menu> menu;

    private String type;

    @Field(name = "도로명주소 ")
    private String roadNameAddress;

    @Field(name = "지번주소")
    private String groundNameAddress;

    private Double mapX;

    private Double mapY;
}
