package com.ourmenu.backend.domain.store.dao;

import com.ourmenu.backend.domain.store.domain.Map;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapRepository extends JpaRepository<Map, Long> {

    Optional<Map> findByMapXAndMapY(Double mapX, Double mapY);

    Optional<Map> findMapById(Long mapId);
}
