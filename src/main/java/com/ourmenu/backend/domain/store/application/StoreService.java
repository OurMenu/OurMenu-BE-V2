package com.ourmenu.backend.domain.store.application;

import com.ourmenu.backend.domain.store.dao.MapRepository;
import com.ourmenu.backend.domain.store.dao.StoreRepository;
import com.ourmenu.backend.domain.store.domain.Map;
import com.ourmenu.backend.domain.store.domain.Store;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final MapRepository mapRepository;

    @Transactional
    public Map saveStoreAndMap(String storeTitle, String storeAddress, Double mapX, Double mapY) {
        Store store = saveStoreIfNonExists(storeTitle, storeAddress);
        return saveMapIfNonExists(mapX, mapY, store);
    }

    private Store saveStoreIfNonExists(String title, String address) {
        Optional<Store> optionalStore = storeRepository.findByTitleAndAddress(title, address);
        Store store;
        if (optionalStore.isPresent()) {
            store = optionalStore.get();
        } else {
            store = Store.builder()
                    .title(title)
                    .address(address)
                    .build();
            store = storeRepository.save(store);
        }
        return store;
    }

    private Map saveMapIfNonExists(Double mapX, Double mapY, Store store) {
        Optional<Map> optionalMap = mapRepository.findByMapXAndMapY(mapX, mapY);
        Map map;
        if (optionalMap.isPresent()) {
            map = optionalMap.get();

        } else {
            map = Map.builder()
                    .mapX(mapX)
                    .mapY(mapY)
                    .store(store)
                    .build();
            map = mapRepository.save(map);
        }
        return map;
    }


}
