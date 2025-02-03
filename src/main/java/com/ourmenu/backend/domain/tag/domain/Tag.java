package com.ourmenu.backend.domain.tag.domain;

import lombok.Getter;

@Getter
public enum Tag {

    KOREA("한식", "집밥이 그리울 땐,", "korea"),
    CHINA("중식", "짜장 먹을까? 짬뽕 먹을까?", "china"),
    JAPAN("일식", "일본 여행이 가고 싶을 땐,", "japan"),
    WESTERN("양식", "기분 내고 싶은 오늘은,", "western"),
    ASIA("아시안", "독특한 향을 느끼고 싶을 땐,", "asia"),
    RICE("밥", "밥이 먹고 싶을 땐,", "rice"),
    BREAD("빵", "빵이 먹고 싶을 땐,", "bread"),
    NOODLE("면", "면이 먹고 싶을 땐,", "noodle"),
    MEAT("고기", "고기 구우러 가고 싶을 땐,", "meat"),
    FISH("생선", "부드러운 속살의 고소한 생선이 떠오를 땐,", "fish"),
    DESSERT("디저트", "달달한 디저트가 땡길 땐,", "dessert"),
    CAFE("카페", "커피가 생각날 땐,", "cafe"),
    FAST_FOOD("패스트푸드", "빠르고 맛있게!", "fast_food"),
    SPICY("매콤함", "스트레스 풀리는 매콤함,", "spicy"),
    SWEET("달달함", "기분 좋아지는 달달함,", "sweet"),
    COOL("시원함", "더위가 사라지는 시원함,", "cool"),
    HOT("뜨끈함", "땀나는 뜨끈함,", "hot"),
    HOT_SPICY("얼큰함", "얼큰함이 살아있는,", "hot_spicy"),
    SOLO("혼밥", "혼자 밥먹기 좋은 곳,", "solo"),
    BUSINESS("비즈니스 미팅", "비즈니스미팅이 있을 땐,", "business"),
    PROMISE("친구 약속", "친구와 약속이 있다면?", "promise"),
    DATE("데이트", "데이트 하는 날엔,", "date"),
    BUY_FOOD("밥약", "밥약하기 좋은 곳,", "buy_food"),
    ORGANIZATION("단체", "단체로 방문한다면,", "organization");

    private final String tagName;
    private final String tagMemo;
    private final String imgUrl;

    Tag(String tagName, String tagMemo, String imgUrl) {
        this.tagName = tagName;
        this.tagMemo = tagMemo;
        this.imgUrl = imgUrl;
    }
}