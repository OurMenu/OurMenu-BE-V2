package com.ourmenu.backend.domain.cache.domain;

public enum MenuFolderIcon {

    ANGRY("angry"),
    BAEKSUK("baeksuk"),
    BASKET("basket"),
    BREAD("bread"),
    CLOUD("cloud"),
    COFFEE("coffee"),
    CONGRATS("congrats"),
    COUPLE("couple"),
    cry("cry"),
    DICE("dice"),
    DOUGHNUT("doughnut"),
    FIRE("fire"),
    FISH("fish"),
    FISH_BREAD("fish-bread"),
    HAMBURGER("hamburger"),
    HEART("heart"),
    ICE_CREAM("ice-cream"),
    JJAMBBONG("jjambbong"),
    LEAF("leaf"),
    MAN("man"),
    MEAT("meat"),
    NOODLE("noodle"),
    PEOPLE("people"),
    RAMEN("ramen"),
    RICE("rice"),
    SMILE("smile"),
    SNOWMAN("snowman"),
    SPOON_AND_CHOPSTICK("spoon-and-chopstick"),
    SUN("sun"),
    SUNNY("sunny"),
    SUSHI("sushi"),
    TABLE("table");


    private String imgUrl;

    MenuFolderIcon(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
