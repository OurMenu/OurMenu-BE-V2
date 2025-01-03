package com.ourmenu.backend.domain.menu.application;

import com.ourmenu.backend.domain.menu.dao.MenuImgRepository;
import com.ourmenu.backend.domain.menu.domain.MenuImg;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MenuImgService {

    private final MenuImgRepository menuImgRepository;
    private final AwsS3Service awsS3Service;

    @Transactional
    public List<MenuImg> saveMenuImgs(Long menuId, List<MultipartFile> multipartFiles) {
        return multipartFiles.stream()
                .map(awsS3Service::uploadFileAsync)
                .map(imgUrl -> this.saveMenuImg(menuId, imgUrl))
                .toList();
    }


    private MenuImg saveMenuImg(Long menuId, String imgUrl) {
        MenuImg menuImg = MenuImg.builder()
                .menuId(menuId)
                .imgUrl(imgUrl)
                .build();
        return menuImgRepository.save(menuImg);
    }

    /**
     * 메뉴 이미지 및 menuImg 삭제
     *
     * @param menuId
     */
    @Transactional
    public void deleteMenuImgs(Long menuId) {
        List<MenuImg> menuImgs = menuImgRepository.findAllByMenuId(menuId);
        menuImgs.forEach(menuImg -> awsS3Service.deleteFileAsync(menuImg.getImgUrl()));
        menuImgRepository.deleteAllByMenuId(menuId);
    }
}
