package com.ourmenu.backend.domain.menu.application.validator;

import com.ourmenu.backend.domain.menu.dao.MenuRepository;
import com.ourmenu.backend.domain.menu.exception.ForbiddenMenuException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuValidator {

    private final MenuRepository menuRepository;

    public void validateExistMenu(Long userId, Long menuId) {
        if (!menuRepository.existsByUserIdAndId(userId, menuId)) {
            throw new ForbiddenMenuException();
        }
    }
}
