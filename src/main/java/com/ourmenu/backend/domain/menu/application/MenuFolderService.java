package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.dao.MenuFolderRepository;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.menu.dto.MenuFolderDto;
import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuFolderService {

    private final AwsS3Service awsS3Service;
    private final MenuFolderRepository menuFolderRepository;

    /**
     * 메뉴 폴더 저장
     *
     * @param menuFolderDto
     * @return 메뉴 폴더 저장 API response
     */
    @Transactional
    public SaveMenuFolderResponse saveMenu(MenuFolderDto menuFolderDto) {
        String imgUrl = awsS3Service.uploadLocalFileAsync(menuFolderDto.getMenuFolderImg());

        MenuFolder menuFolder = saveMenuFolder(menuFolderDto, imgUrl);
        SaveMenuFolderResponse build = SaveMenuFolderResponse.builder()
                .menuFolderTitle(menuFolder.getTitle())
                .menuFolderUrl(imgUrl)
                .menuFolderIcon(menuFolder.getIcon())
                .userId(menuFolderDto.getUserId())
                .build();
        return build;
    }

    /**
     * 메뉴폴더 저장
     *
     * @param menuFolderDto
     * @param imgUrl
     * @return 저장된 메뉴 폴더 엔티티
     */
    private MenuFolder saveMenuFolder(MenuFolderDto menuFolderDto, String imgUrl) {
        MenuFolder menuFolder = MenuFolder.builder()
                .title(menuFolderDto.getMenuFolderTitle())
                .imgUrl(imgUrl)
                .icon(menuFolderDto.getMenuFolderIcon())
                .index(menuFolderRepository.findMaxIndex() + 1)
                .userId(menuFolderDto.getUserId())
                .build();

        return menuFolderRepository.save(menuFolder);
    }

}
