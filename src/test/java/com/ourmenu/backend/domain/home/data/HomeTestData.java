package com.ourmenu.backend.domain.home.data;

import com.ourmenu.backend.domain.cache.domain.MenuPin;
import com.ourmenu.backend.domain.home.domain.HomeQuestionAnswer;
import com.ourmenu.backend.domain.home.domain.Question;
import com.ourmenu.backend.domain.menu.domain.Menu;
import com.ourmenu.backend.domain.store.domain.Map;
import com.ourmenu.backend.domain.store.domain.Store;
import com.ourmenu.backend.domain.tag.domain.MenuTag;
import com.ourmenu.backend.domain.tag.domain.Tag;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import jakarta.persistence.EntityManager;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.transaction.annotation.Transactional;

public class HomeTestData {

    EntityManager entityManager;

    public HomeTestData(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public HomeQuestionAnswer createTestQuestion(Long userId) {
        HomeQuestionAnswer homeQuestionAnswer = HomeQuestionAnswer.builder()
                .question(Question.FEEL)
                .userId(userId)
                .build();
        entityManager.persist(homeQuestionAnswer);
        return homeQuestionAnswer;
    }

    /**
     * 홈 화면에 필요한 테스트 데이터를 저장한다
     *
     * @param customUserDetails
     */
    @Transactional
    public void createTestHomeMenu(CustomUserDetails customUserDetails) {
        int srid = 4326;
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), srid);

        Coordinate coordinate = new Coordinate(127.085, 37.538);
        Point location = geometryFactory.createPoint(coordinate);

        Map map = Map.builder()
                .location(location)
                .build();
        entityManager.persist(map);

        Store store = Store.builder()
                .title("테스트 식당1")
                .address("광진구 어딘가")
                .map(map)
                .build();
        entityManager.persist(store);

        createTestMenu("테스트 메뉴1 비비큐", MenuPin.BBQ, Tag.PROMISE, store, customUserDetails);
        createTestMenu("테스트 메뉴2 빵", MenuPin.BREAD, Tag.PROMISE, store, customUserDetails);

        HomeQuestionAnswer homeQuestionAnswer = HomeQuestionAnswer.builder()
                .question(Question.FEEL)
                .userId(customUserDetails.getId())
                .build();
        entityManager.persist(homeQuestionAnswer);
    }

    /**
     * 메뉴와 관련된 엔티티를 모두 저장
     *
     * @param title
     * @param menuPin
     * @param tag
     * @param store
     * @param customUserDetails
     */
    private void createTestMenu(String title, MenuPin menuPin, Tag tag, Store store,
                                CustomUserDetails customUserDetails) {
        Menu menu = Menu.builder()
                .title(title)
                .price(1000)
                .pin(MenuPin.BBQ)
                .memoTitle("테스트 메뉴1 메모 제목")
                .memoContent("테스트 메뉴1 메모 내용")
                .store(store)
                .isCrawled(Boolean.FALSE)
                .userId(customUserDetails.getId())
                .build();

        entityManager.persist(menu);

        MenuTag menuTag = MenuTag.builder()
                .menuId(menu.getId())
                .tag(tag)
                .build();

        entityManager.persist(menuTag);
    }
}
