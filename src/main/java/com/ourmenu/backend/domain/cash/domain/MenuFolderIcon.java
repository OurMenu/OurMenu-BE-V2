package com.ourmenu.backend.domain.cash.domain;

public enum MenuFolderIcon {

    BAKERY("빵", "bakery.svg"),
    BBQ("비비큐", "bbq.svg"),
    CAKE("케익", "cake.svg"),
    COFFEE("커피", "coffee.svg"),
    CUTLERY("수저", "cutlery.svg"),
    DOT("점(기본)", "dot.svg"),
    ETC("기타", "etc.svg"),
    FISH("물고기", "fish.svg"),
    HAMBURGER("햄버거", "hamburger.svg"),
    KOREA("한국", "korea.svg"),
    MEAT("고기", "meat.svg"),
    NOODLE("면요리", "noodle.svg"),
    PIZZA("피자", "pizza.svg"),
    POT("냄비", "pot.svg"),
    RICE("쌀밥", "rice.svg"),
    SAUSAGE("소시지", "sausage.svg"),
    SKEWER("꼬치", "skewer.svg"),
    STAR("별", "star.svg"),
    SUSHI("초밥", "sushi.svg"),
    TROPICAL("열대", "tropical.svg");

    private String name;
    private String imgUrl;

    MenuFolderIcon(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
