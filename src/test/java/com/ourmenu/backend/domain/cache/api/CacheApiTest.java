package com.ourmenu.backend.domain.cache.api;

import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.cache.dto.GetCacheInfoResponse;
import com.ourmenu.backend.domain.cache.dto.SimpleHomeImgResponse;
import com.ourmenu.backend.domain.cache.dto.SimpleMenuFolderIconResponse;
import com.ourmenu.backend.domain.cache.dto.SimpleMenuPinResponse;
import com.ourmenu.backend.domain.cache.dto.SimpleTagImgResponse;
import com.ourmenu.backend.domain.cache.util.UrlConverter;
import com.ourmenu.backend.global.TestConfig;
import com.ourmenu.backend.global.config.GlobalDataConfig;
import com.ourmenu.backend.global.data.GlobalUserTestData;
import com.ourmenu.backend.global.response.ApiResponse;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@Import({GlobalDataConfig.class, TestConfig.class})
@DisplayName("캐시 통합 테스트")
public class CacheApiTest {

    @Autowired
    CacheController cacheController;

    @Autowired
    GlobalUserTestData userTestData;

    @Autowired
    UrlConverter urlConvertor;

    RestTemplate restTemplate = new RestTemplate();

    @Test
    void 확인할_수_없는_이미지는_조회할_수_없다() {
        //given

        //when
        String menuPinMapUrl = urlConvertor.getMenuPinMapUrl(MenuPin.BBQ);

        //then
        Assertions.assertThatThrownBy(
                        () -> restTemplate.getForEntity(
                                menuPinMapUrl + "trash", String.class))
                .isInstanceOf(HttpClientErrorException.class);
    }

    @Test
    void 메뉴판_캐시_이미지는_조회할_수_있다() {
        //given
        ApiResponse<GetCacheInfoResponse> response = cacheController.getCacheInfo();
        GetCacheInfoResponse getCacheInfoResponse = response.getResponse();

        //when
        List<SimpleMenuFolderIconResponse> menuFolderIcons = getCacheInfoResponse.getMenuFolderIcons();

        //then
        menuFolderIcons.stream().forEach(simpleMenuFolderIconResponse -> {
            String menuFolderIconUrl = simpleMenuFolderIconResponse.getMenuFolderIconUrl();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(menuFolderIconUrl, String.class);
            Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
            Assertions.assertThat(responseEntity.getBody()).isNotEmpty();
        });
    }

    @Test
    void 메뉴핀_캐시_이미지는_조회할_수_있다() {
        //given
        ApiResponse<GetCacheInfoResponse> response = cacheController.getCacheInfo();
        GetCacheInfoResponse getCacheInfoResponse = response.getResponse();

        List<SimpleMenuPinResponse> menuPins = getCacheInfoResponse.getMenuPins();

        //then
        menuPins.stream().forEach(simpleMenuFolderIconResponse -> {
            String menuPinAddImgUrl = simpleMenuFolderIconResponse.getMenuPinAddImgUrl();
            if (menuPinAddImgUrl == null) {
                return;
            }
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(menuPinAddImgUrl, String.class);
            Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
            Assertions.assertThat(responseEntity.getBody()).isNotEmpty();
        });

        menuPins.stream().forEach(simpleMenuFolderIconResponse -> {
            String menuPinAddDisableImgUrl = simpleMenuFolderIconResponse.getMenuPinAddDisableImgUrl();
            if (menuPinAddDisableImgUrl == null) {
                return;
            }
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(menuPinAddDisableImgUrl, String.class);
            Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
            Assertions.assertThat(responseEntity.getBody()).isNotEmpty();
        });

        menuPins.stream().forEach(simpleMenuFolderIconResponse -> {
            String menuPinMapImgUrl = simpleMenuFolderIconResponse.getMenuPinMapImgUrl();
            if (menuPinMapImgUrl == null) {
                return;
            }

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(menuPinMapImgUrl, String.class);
            Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
            Assertions.assertThat(responseEntity.getBody()).isNotEmpty();
        });

    }

    @Test
    void 홈이미지_캐시_이미지는_조회할_수_있다() {
        //given
        ApiResponse<GetCacheInfoResponse> response = cacheController.getCacheInfo();
        GetCacheInfoResponse getCacheInfoResponse = response.getResponse();

        //when
        List<SimpleHomeImgResponse> homeImgs = getCacheInfoResponse.getHomeImgs();

        //then
        homeImgs.stream().forEach(simpleMenuFolderIconResponse -> {
            String homeImgUrl = simpleMenuFolderIconResponse.getHomeImgUrl();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(homeImgUrl, String.class);
            Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
            Assertions.assertThat(responseEntity.getBody()).isNotEmpty();
        });
    }

    @Test
    void 태그_캐시_이미지는_조회할_수_있다() {
        //given
        ApiResponse<GetCacheInfoResponse> response = cacheController.getCacheInfo();
        GetCacheInfoResponse getCacheInfoResponse = response.getResponse();

        //when
        List<SimpleTagImgResponse> tags = getCacheInfoResponse.getTags();

        //then
        tags.stream().forEach(simpleMenuFolderIconResponse -> {
            String orangeTagImgUrl = simpleMenuFolderIconResponse.getOrangeTagImgUrl();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(orangeTagImgUrl, String.class);
            Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
            Assertions.assertThat(responseEntity.getBody()).isNotEmpty();
        });

        tags.stream().forEach(simpleMenuFolderIconResponse -> {
            String whiteTagImgUrl = simpleMenuFolderIconResponse.getWhiteTagImgUrl();
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(whiteTagImgUrl, String.class);
            Assertions.assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
            Assertions.assertThat(responseEntity.getBody()).isNotEmpty();
        });
    }
}
