package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.cache.util.UrlConverter;
import com.ourmenu.backend.domain.menu.dao.MenuFolderRepository;
import com.ourmenu.backend.domain.menu.dao.MenuRepository;
import com.ourmenu.backend.domain.menu.domain.MenuFolder;
import com.ourmenu.backend.domain.menu.domain.MenuMenuFolder;
import com.ourmenu.backend.domain.menu.dto.GetMenuFolderResponse;
import com.ourmenu.backend.domain.menu.dto.MenuFolderDto;
import com.ourmenu.backend.domain.menu.dto.MenuFolderResponse;
import com.ourmenu.backend.domain.menu.dto.SaveMenuFolderResponse;
import com.ourmenu.backend.domain.menu.dto.UpdateMenuFolderResponse;
import com.ourmenu.backend.domain.menu.exception.ForbiddenMenuFolderException;
import com.ourmenu.backend.domain.menu.exception.NotFoundMenuFolderException;
import com.ourmenu.backend.domain.menu.exception.OutOfBoundCustomIndexException;
import com.ourmenu.backend.domain.menu.util.DefaultImgConverter;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuFolderService {

    private final AwsS3Service awsS3Service;
    private final MenuFolderRepository menuFolderRepository;
    private final MenuMenuFolderService menuMenuFolderService;
    private final DefaultImgConverter defaultImgConvertor;
    private final MenuRepository menuRepository;
    private final UrlConverter urlConverter;

    /**
     * 메뉴 폴더 저장
     *
     * @param menuFolderDto
     * @return 메뉴 폴더 저장 API response
     */
    @Transactional
    public SaveMenuFolderResponse saveMenuFolder(MenuFolderDto menuFolderDto) {
        String menuFolderImgUrl = Optional.ofNullable(menuFolderDto.getMenuFolderImg())
                .map(awsS3Service::uploadFileAsync)
                .orElse(null);

        MenuFolder menuFolder = saveMenuFolder(menuFolderDto, menuFolderImgUrl);
        return SaveMenuFolderResponse.of(menuFolder, menuFolderDto.getMenuIds(),
                defaultImgConvertor.getDefaultMenuFolderImgUrl(), urlConverter);
    }

    /**
     * 메뉴판 삭제 (인덱스 조정)
     *
     * @param userId
     * @param menuFolderId
     */
    @Transactional
    public void deleteMenuFolder(Long userId, Long menuFolderId) {
        MenuFolder menuFolder = findOne(userId, menuFolderId);
        menuFolderRepository.delete(menuFolder);
        int maxIndex = menuFolderRepository.findMaxIndex();

        if (menuFolder.getIndex() != maxIndex) {
            menuFolderRepository.decrementIndexes(userId, (menuFolder.getIndex() + 1), maxIndex);
        }

        if (menuFolder.getImgUrl() != null) {
            awsS3Service.deleteFileAsync(menuFolder.getImgUrl());
        }
    }

    @Transactional
    public UpdateMenuFolderResponse updateMenuFolder(Long userId, Long menuFolderId, MenuFolderDto menuFolderDto,
                                                     Boolean isImageModified) {

        MenuFolder menuFolder = findOne(userId, menuFolderId);
        if (menuFolderDto.getMenuIds() != null) {
            menuMenuFolderService.updateMenuMenuFolder(userId, menuFolderId, menuFolderDto.getMenuIds());
        }

        if (isImageModified) {
            //기존에 있는 이미지는 삭제한다
            if (menuFolder.getImgUrl() != null) {
                awsS3Service.deleteFileAsync(menuFolder.getImgUrl());
                menuFolder.updateImg(null);
            }

            //추가할 이미지가 있으면 추가한다
            if (menuFolderDto.getMenuFolderImg() != null) {
                String imgUrl = awsS3Service.uploadFileAsync(menuFolderDto.getMenuFolderImg());

                menuFolder.updateImg(imgUrl);
            }
        }
        menuFolder.update(menuFolderDto);
        List<MenuMenuFolder> menuMenuFolders = menuMenuFolderService.findAllByMenuFolderId(menuFolderId);
        return UpdateMenuFolderResponse.of(menuFolder, menuMenuFolders,
                defaultImgConvertor.getDefaultMenuFolderImgUrl(), urlConverter);
    }

    /**
     * 메뉴판 인덱스 수정
     *
     * @param userId
     * @param menuFolderId
     * @param index
     */
    @Transactional
    public UpdateMenuFolderResponse updateMenuFolderIndex(Long userId, Long menuFolderId, int index) {
        MenuFolder findMenuFolder = findOne(userId, menuFolderId);
        int maxIndex = menuFolderRepository.findMaxIndex();

        if (maxIndex < index) {
            throw new OutOfBoundCustomIndexException();
        }

        int preIndex = findMenuFolder.getIndex();

        if (index > preIndex) {//index를 높이는 경우(앞에 놓는 경우)
            menuFolderRepository.decrementIndexes(userId, preIndex + 1, index);
        }
        if (index < preIndex) {//index를 낮추는 경우(뒤에 놓는 경우)
            menuFolderRepository.incrementIndexes(userId, index, preIndex - 1);
        }

        findMenuFolder.updateIndex(index);
        List<MenuMenuFolder> menuMenuFolders = menuMenuFolderService.findAllByMenuFolderId(menuFolderId);
        return UpdateMenuFolderResponse.of(findMenuFolder, menuMenuFolders,
                defaultImgConvertor.getDefaultMenuFolderImgUrl(), urlConverter);
    }


    /**
     * 유저 메뉴판 정보 조회
     *
     * @param userId
     * @return index 기준 내림 차순 메뉴판 리스트
     */
    public GetMenuFolderResponse findAllMenuFolder(Long userId) {
        List<MenuFolder> menuFolders = menuFolderRepository.findAllByUserIdOrderByIndexDesc(userId);

        List<MenuFolderResponse> menuFolderResponses = menuFolders.stream()
                .map(menuFolder -> {
                    List<MenuMenuFolder> menuMenuFolders = menuMenuFolderService.findAllByMenuFolderId(
                            menuFolder.getId());
                    return MenuFolderResponse.of(menuFolder, menuMenuFolders,
                            defaultImgConvertor.getDefaultMenuFolderImgUrl(), urlConverter);
                }).toList();
        int menuCount = menuRepository.countByUserId(userId);
        return GetMenuFolderResponse.of(menuCount, menuFolderResponses);
    }

    public List<MenuFolder> findAllByMenuId(Long menuId) {
        return menuFolderRepository.findMenuFoldersByMenuId(menuId);
    }

    public MenuFolder findOne(Long userId, Long menuFolderId) {
        MenuFolder menuFolder = menuFolderRepository.findById(menuFolderId)
                .orElseThrow(NotFoundMenuFolderException::new);
        Long findUserId = menuFolder.getUserId();
        if (!userId.equals(findUserId)) {
            throw new ForbiddenMenuFolderException();
        }
        return menuFolder;
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
        MenuFolder saveMenuFolder = menuFolderRepository.save(menuFolder);
        menuMenuFolderService.saveMenuMenuFolders(menuFolderDto.getMenuIds(), menuFolder.getUserId(), saveMenuFolder);
        return saveMenuFolder;
    }
}
