package com.ourmenu.backend.domain.menu.api;

import com.ourmenu.backend.domain.menu.application.MenuService;
import com.ourmenu.backend.domain.menu.dto.SaveMenuRequest;
import com.ourmenu.backend.domain.menu.dto.SaveMenuResponse;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping()
    public void saveMenu(@RequestBody SaveMenuRequest request,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        SaveMenuResponse response = menuService.saveMenu(userDetails.getId(), request);
    }
}
