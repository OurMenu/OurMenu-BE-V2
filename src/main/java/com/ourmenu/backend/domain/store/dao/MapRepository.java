package com.ourmenu.backend.domain.store.dao;

import com.ourmenu.backend.domain.store.domain.Map;
import java.util.Optional;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends JpaRepository<Map, Long> {

    Optional<Map> findByLocation(Point location);

    @Query("""
        SELECT m
        FROM Map m
            JOIN FETCH m.stores s
        WHERE m.id = :mapId
    """)
    Optional<Map> findMapById(Long mapId);
}
