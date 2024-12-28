package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.dao.MenuFolderRepository;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.menu.dto.MenuFolderDto;
import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderResponse;
import com.ourmenu.backend.domain.menu.dto.UpdateMenuFolderResponse;
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
    public SaveMenuFolderResponse saveMenuFolder(MenuFolderDto menuFolderDto) {
        String menuFolderImgUrl = awsS3Service.uploadLocalFileAsync(menuFolderDto.getMenuFolderImg());
        MenuFolder menuFolder = saveMenuFolder(menuFolderDto, menuFolderImgUrl);
        return SaveMenuFolderResponse.from(menuFolder);
    }

    @Transactional
    public void deleteMenuFolder(Long userId, Long menuFolderId) {
        MenuFolder menuFolder = findOneByOwn(userId, menuFolderId);
        menuFolderRepository.delete(menuFolder);
    }

    @Transactional
    public UpdateMenuFolderResponse updateMenuFolder(Long userId, Long menuFolderId, MenuFolderDto menuFolderDto) {

        MenuFolder menuFolder = findOneByOwn(userId, menuFolderId);

        if (menuFolderDto.getMenuFolderImg() != null) {
            String imgUrl = awsS3Service.uploadLocalFileAsync(menuFolderDto.getMenuFolderImg());
            menuFolder.update(menuFolderDto, imgUrl);
            return UpdateMenuFolderResponse.from(menuFolder);
        }
        menuFolder.update(menuFolderDto, null);
        return UpdateMenuFolderResponse.from(menuFolder);
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

    public MenuFolder findOneByOwn(Long userId, Long menuFolderId) {
        MenuFolder menuFolder = findOne(menuFolderId);
        Long findUserId = menuFolder.getUserId();
        if (!userId.equals(findUserId)) {
            throw new RuntimeException("소유하고 있는 메뉴판이 아닙니다");
        }
        return menuFolder;
    }

    private MenuFolder findOne(Long menuFolderId) {
        return menuFolderRepository.findById(menuFolderId)
                .orElseThrow(() -> new RuntimeException("찾을 수 없는 메뉴판입니다"));
    }

}
