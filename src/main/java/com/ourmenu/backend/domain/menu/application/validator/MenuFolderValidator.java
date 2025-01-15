package com.ourmenu.backend.domain.menu.application.validator;

import com.ourmenu.backend.domain.menu.dao.MenuFolderRepository;
import com.ourmenu.backend.domain.menu.exception.ForbiddenMenuFolderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuFolderValidator {

    private final MenuFolderRepository menuFolderRepository;

    /**
     * 메뉴판 소유 여부 확인
     *
     * @param userId
     * @param menuFolderId
     * @return
     */
    @Transactional(readOnly = true)
    public void validateExistMenuFolder(Long userId, Long menuFolderId) {
        if (!menuFolderRepository.existsByUserIdAndId(menuFolderId, userId)) {
            throw new ForbiddenMenuFolderException();
        }
    }
}
