package com.ourmenu.backend.domain.cache.domain;

public enum MenuPin {

    BAKERY("빵", "bakery"),
    BBQ("비비큐", "bbq"),
    CAKE("케익", "cake"),
    COFFEE("커피", "coffee"),
    CUTLERY("수저", "cutlery"),
    DOT("점(기본)", "dot"),
    ETC("기타", "etc"),
    FISH("물고기", "fish"),
    HAMBURGER("햄버거", "hamburger"),
    KOREA("한국", "korea"),
    MEAT("고기", "meat"),
    NOODLE("면요리", "noodle"),
    PIZZA("피자", "pizza"),
    POT("냄비", "pot"),
    RICE("쌀밥", "rice"),
    SAUSAGE("소시지", "sausage"),
    SKEWER("꼬치", "skewer"),
    STAR("별", "star"),
    SUSHI("초밥", "sushi"),
    TROPICAL("열대", "tropical");

    private String name;
    private String imgUrl;

    MenuPin(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
