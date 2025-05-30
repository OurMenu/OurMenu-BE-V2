package com.ourmenu.backend.domain.cache.util;

import com.ourmenu.backend.domain.cache.domain.HomeImg;
import com.ourmenu.backend.domain.cache.domain.MenuFolderIcon;
import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.home.domain.Answer;
import com.ourmenu.backend.domain.tag.domain.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UrlConverter {

    @Value("${spring.cloud.aws.credentials.default.bucket.url}")
    private String url;

    public String getMenuPinMapUrl(MenuPin pin) {
        return url + "/menu-pins/" + pin.getImgUrl() + "_map.png";
    }

    public String getMenuPinAddUrl(MenuPin pin) {
        if (isETCMenuPin(pin)) {
            return null;
        }
        return url + "/menu-pins/" + pin.getImgUrl() + "_add.png";
    }

    public String getMenuPinMapAddDisable(MenuPin pin) {
        return url + "/menu-pins/" + pin.getImgUrl() + "_add_disable.png";
    }

    public String getMenuFolderImgUrl(MenuFolderIcon icon) {
        return url + "/menu-folder-icons/" + icon.getImgUrl() + ".svg";
    }

    public String getHomeImgUrl(HomeImg homeImg) {
        return url + "/homes/" + homeImg.getImgUrl() + ".svg";
    }

    public String getOrangeTagImgUrl(Tag tag) {
        return url + "/tags/" + tag.getImgUrl() + "_orange.svg";
    }

    public String getWhiteTagImgUrl(Tag tag) {
        return url + "/tags/" + tag.getImgUrl() + "_white.svg";
    }

    public String getAnswerImgUrl(Answer answer) {
        return url + "/answers/" + answer.toString() + ".svg";
    }

    public String getHomeRecommendTagImgUrl(Tag tag) {
        return url + "/home_tags/" + tag.getTagEnum() + ".svg";
    }

    private boolean isETCMenuPin(MenuPin pin) {
        return pin.equals(MenuPin.TWO) || pin.equals(MenuPin.THREE) || pin.equals(MenuPin.FOUR) || pin.equals(
                MenuPin.FIVE);
    }
}