package com.ourmenu.backend.domain.search.dao;

import com.ourmenu.backend.domain.search.domain.SearchableStore;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchableStoreRepository extends MongoRepository<SearchableStore, String> {

    List<SearchableStore> findAllByNameContaining(String name);
}
