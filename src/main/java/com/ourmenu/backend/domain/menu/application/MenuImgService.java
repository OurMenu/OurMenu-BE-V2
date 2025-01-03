package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.dao.MenuImgRepository;
import com.ourmenu.backend.domain.menu.domain.MenuImg;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuImgService {

    private final MenuImgRepository menuImgRepository;

    @Transactional
    public List<MenuImg> saveMenuImgs(Long menuId, List<String> imgUrls) {
        return imgUrls.stream().map(imgUrl -> this.saveMenuImg(menuId, imgUrl))
                .toList();
    }


    private MenuImg saveMenuImg(Long menuId, String imgUrl) {
        MenuImg menuImg = MenuImg.builder()
                .menuId(menuId)
                .imgUrl(imgUrl)
                .build();
        return menuImgRepository.save(menuImg);
    }
}
