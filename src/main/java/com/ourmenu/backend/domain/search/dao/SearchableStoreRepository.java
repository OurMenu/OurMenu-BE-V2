package com.ourmenu.backend.domain.search.dao;

import com.ourmenu.backend.domain.search.domain.SearchableStore;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchableStoreRepository extends MongoRepository<SearchableStore, String> {

    @Query("{ '$or': [ { 'menus.menuName': { $regex: ?0, $options: 'i' } }, { 'name': { $regex: ?0, $options: 'i' } } ] ,"
            + "'location': { $nearSphere: { $geometry: { type: 'Point', coordinates: [?1, ?2] } } } }")
    List<SearchableStore> findByMenuNameOrStoreNameContaining(String keyword, double mapX, double mapY,
                                                              Pageable pageable);

    Optional<SearchableStore> findByStoreId(String storeId);
}
