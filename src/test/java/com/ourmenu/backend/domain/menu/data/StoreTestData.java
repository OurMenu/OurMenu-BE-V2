package com.ourmenu.backend.domain.menu.data;

import com.ourmenu.backend.domain.store.domain.Map;
import com.ourmenu.backend.domain.store.domain.Store;
import jakarta.persistence.EntityManager;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class StoreTestData {

    private EntityManager entityManager;

    public StoreTestData(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Store createTestStore() {
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
        return store;
    }
}
