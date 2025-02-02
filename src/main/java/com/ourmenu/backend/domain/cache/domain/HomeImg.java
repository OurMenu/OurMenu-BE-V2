package com.ourmenu.backend.domain.cache.domain;

public enum HomeImg {

    LIKE1("기분이 좋을 땐 친구들과 약속 어때요", "like1"),
    LIKE2("기분이 좋을 땐 친구들과 약속 어때요", "like2"),
    DISLIKE1("저기압일 땐 지금 당장 고기앞으로 가라", "dislike1"),
    DISLIKE2("기분이 꿀꿀할 때는 돼지파티다", "dislike2"),
    SUNNY1("맑은 라인 오늘 바깥에서 피크닉 어때요", "sunny1"),
    RAINY1("비오고 쌀쌀할때 뜨끈한 국물 어때요", "rainy1"),
    SWEET1("스트레스 해소엔 달달한게 최고지", "sweet1"),
    SWEET2("스트레스 만땅 달달함으로 풀어볼까요", "sweet2"),
    SPICY1("스트레스 해소엔 역시 불닭으로 화끈하게", "spicy1"),
    SEA1("바다 내음이 물씬 풍기는 해산물 어때요", "sea1"),
    SEA2("바다 내음이 물씬 풍기는 해산물 어때요", "sea2"),
    MOUNTAIN1("피톤치드 향 가득 느낄 수 있는 건강식 어때요", "mountain1"),
    SUMMER1("무더운 여름엔 이열치열로 땀좀 빼 볼까요", "summer1"),
    SUMMER2("따가운 햇살엔 이열치열! 뜨거운 음식들", "summer2"),
    SUMMER3("햇살이 뜨거울때 차가운 음식들로 이겨내 볼까요", "summer3"),
    WINTER1("추운 겨울엔 뜨끈한 국물 어때요", "winter1"),
    WINTER2("추운 겨울의 따끈따근한 제철 음식이에요", "winter2");

    private final String description;
    private final String imgUrl;

    HomeImg(String description, String imgUrl) {
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}