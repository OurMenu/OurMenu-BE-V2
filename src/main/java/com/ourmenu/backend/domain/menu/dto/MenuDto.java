package com.ourmenu.backend.domain.menu.dto;

import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.search.dto.SimpleSearchDto;
import com.ourmenu.backend.domain.tag.domain.Tag;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class MenuDto {

    private List<MultipartFile> menuImgs;
    private String menuTitle;
    private int menuPrice;
    private String menuMemoTitle;
    private String menuMemoContent;
    private MenuPin menuPin;
    private List<Long> menuFolderIds;
    private String storeId;
    private boolean isCrawled;
    private List<Tag> tags;
    private String storeTitle;
    private String storeAddress;
    private Double mapX;
    private Double mapY;
    private Long userId;

    public static MenuDto of(SaveMenuRequest request, List<MultipartFile> menuFolderImgs,
                             CustomUserDetails userDetails, SimpleSearchDto simpleSearchDto) {
        return MenuDto.builder()
                .menuImgs(menuFolderImgs)
                .menuTitle(request.getMenuTitle())
                .menuPrice(request.getMenuPrice())
                .menuMemoTitle(request.getMenuMemoTitle())
                .menuMemoContent(request.getMenuMemoContent())
                .menuPin(request.getMenuPin())
                .menuFolderIds(request.getMenuFolderIds())
                .isCrawled(request.isCrawled())
                .tags(request.getTags())
                .storeTitle(simpleSearchDto.getStoreTitle())
                .storeAddress(simpleSearchDto.getStoreAddress())
                .mapX(simpleSearchDto.getMapX())
                .mapY(simpleSearchDto.getMapY())
                .userId(userDetails.getId())
                .build();
    }
}
