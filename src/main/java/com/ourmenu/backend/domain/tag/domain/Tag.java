package com.ourmenu.backend.domain.tag.domain;

import lombok.Getter;

@Getter
public enum Tag {

    KOREA("KOREA", "한식", "집밥이 그리울 땐,"),
    CHINA("CHINA", "중식", "짜장 먹을까? 짬뽕 먹을까?"),
    JAPAN("JAPAN", "일식", "일본 여행이 가고 싶을 땐,"),
    WESTERN("WESTERN", "양식", "기분 내고 싶은 오늘은,"),
    ASIA("ASIA", "아시안", "독특한 향을 느끼고 싶을 땐,"),
    RICE("RICE", "밥", "밥이 먹고 싶을 땐,"),
    BREAD("BREAD", "빵", "빵이 먹고 싶을 땐,"),
    NOODLE("NOODLE", "면", "면이 먹고 싶을 땐,"),
    MEAT("MEAT", "고기", "고기 구우러 가고 싶을 땐,"),
    FISH("FISH", "생선", "부드러운 속살의 고소한 생선이 떠오를 땐,"),
    DESSERT("DESSERT", "디저트", "달달한 디저트가 땡길 땐,"),
    CAFE("CAFE", "카페", "커피가 생각날 땐,"),
    FAST_FOOD("FAST_FOOD", "패스트푸드", "빠르고 맛있게!"),
    SPICY("SPICY", "매콤함", "스트레스 풀리는 매콤함,"),
    SWEET("SWEET", "달달함", "기분 좋아지는 달달함,"),
    COOL("COOL", "시원함", "더위가 사라지는 시원함,"),
    HOT("HOT", "뜨끈함", "땀나는 뜨끈함,"),
    HOT_SPICY("HOT_SPICY", "얼큰함", "얼큰함이 살아있는, "),
    SOLO("SOLO", "혼밥", "혼자 밥먹기 좋은 곳,"),
    WARM("WARM", "뜨끈함", "땀나는 뜨끈함,"),
    BUSINESS("BUSINESS", "비즈니스 미팅", "비즈니스미팅이 있을 땐,"),
    PROMISE("PROMISE", "친구 약속", "친구와 약속이 있다면?"),
    DATE("DATE", "데이트", "데이트 하는 날엔,"),
    BUY_FOOD("BUY_FOOD", "밥약", "밥약하기 좋은 곳,"),
    ORGANIZATION("ORGANIZATION", "단체", "단체로 방문한다면,");

    private final String TagEnum;
    private final String TagName;
    private final String TagMemo;

    Tag(String tagEnum, String tagName, String tagMemo) {
        TagEnum = tagEnum;
        TagName = tagName;
        TagMemo = tagMemo;
    }
}