package com.ourmenu.backend.domain.store.dao;

import com.ourmenu.backend.domain.store.domain.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Optional<Store> findByTitleAndAddress(String title, String address);
}
