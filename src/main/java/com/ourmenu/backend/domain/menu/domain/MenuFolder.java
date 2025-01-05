package com.ourmenu.backend.domain.menu.domain;

import com.ourmenu.backend.domain.menu.dto.MenuFolderDto;
import com.ourmenu.backend.global.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menu_folder")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class MenuFolder extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    private String imgUrl;

    private String icon;

    @NotNull
    @Column(name = "custom_index")
    private int index;

    @NotNull
    private Long userId;

    public void update(MenuFolderDto menuFolderDto, String imgUrl) {
        String title = menuFolderDto.getMenuFolderTitle();
        if (title != null) {
            this.title = title;
        }

        String icon = menuFolderDto.getMenuFolderIcon();
        if (icon != null) {
            this.icon = icon;
        }

        if (imgUrl != null) {
            this.imgUrl = imgUrl;
        }
    }

    public void updateIndex(int index) {
        this.index = index;
    }

}
