package com.ourmenu.backend.domain.home.domain;

import static com.ourmenu.backend.domain.cache.domain.HomeImg.DISLIKE1;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.DISLIKE2;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.LIKE1;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.LIKE2;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.MOUNTAIN1;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.RAINY1;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.SEA1;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.SEA2;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.SPICY1;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.SUMMER1;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.SUMMER2;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.SUMMER3;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.SUNNY1;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.SWEET1;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.SWEET2;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.WINTER1;
import static com.ourmenu.backend.domain.cache.domain.HomeImg.WINTER2;

import com.ourmenu.backend.domain.cache.domain.HomeImg;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;

@Getter
public enum Answer {

    LIKE("좋아!", LIKE1, LIKE2),
    DISLIKE("별로야..", DISLIKE1, DISLIKE2),
    SUNNY("맑아", SUNNY1),
    RAINY("비가 와", RAINY1),
    SWEET("달달한 음식", SWEET1, SWEET2),
    SPICY("매운 음식", SPICY1),
    SEA("바다", SEA1, SEA2),
    MOUNTAIN("산", MOUNTAIN1),
    SUMMER("여름", SUMMER1, SUMMER2, SUMMER3),
    WINTER("겨울", WINTER1, WINTER2);

    private String answer;
    private List<HomeImg> homeImgs;

    Answer(String answer, HomeImg... homeImgs) {
        this.answer = answer;
        this.homeImgs = Arrays.asList(homeImgs);
    }
}
