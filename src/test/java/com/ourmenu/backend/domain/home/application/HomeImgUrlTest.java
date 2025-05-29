package com.ourmenu.backend.domain.home.application;

import com.ourmenu.backend.domain.cache.domain.HomeImg;
import com.ourmenu.backend.domain.cache.util.UrlConverter;
import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@DisplayName("홈 이미지 URL 통합 테스트")
public class HomeImgUrlTest {

    @Autowired
    UrlConverter urlConverter;

    RestTemplate restTemplate = new RestTemplate();

    @Test
    void 홈_이미지를_모두_조회_할_수_있다() {
        //given
        List<HomeImg> homeImgs = Arrays.stream(HomeImg.values()).toList();

        //when

        //then
        homeImgs.stream().forEach(homeImg -> {
            String homeImgUrl = urlConverter.getHomeImgUrl(homeImg);
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(homeImgUrl, String.class);
            Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
            Assertions.assertThat(responseEntity.getBody()).isNotEmpty();
        });
    }

    @Test
    void 홈_추천문구_이미지를_모두_조회_할_수_있다() {
        //given
        List<Tag> tags = Arrays.stream(Tag.values()).toList();

        //when

        //then
        tags.stream().forEach(tag -> {
            String homeRecommendTagImgUrl = urlConverter.getHomeRecommendTagImgUrl(tag);
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(homeRecommendTagImgUrl, String.class);
            Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
            Assertions.assertThat(responseEntity.getBody()).isNotEmpty();
        });

    }
}
