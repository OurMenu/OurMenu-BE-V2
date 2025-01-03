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
    public Store saveStoreAndMap(String storeTitle, String storeAddress, Double mapX, Double mapY) {
        Map map = saveMapIfNonExists(mapX, mapY);
        return saveStoreIfNonExists(storeTitle, storeAddress, map);
    }

    /**
     * 메뉴 - 가게 - 위치 삭제전파 참조하는 엔티티가 없어야한다.
     *
     * @param store
     */
    @Transactional
    public void deleteStore(Store store) {
        return;
    }

    private Store saveStoreIfNonExists(String title, String address, Map map) {
        Optional<Store> optionalStore = storeRepository.findByTitleAndAddress(title, address);
        Store store;
        if (optionalStore.isPresent()) {
            store = optionalStore.get();
        } else {
            store = Store.builder()
                    .title(title)
                    .address(address)
                    .map(map)
                    .build();
            store = storeRepository.save(store);
        }
        return store;
    }

    private Map saveMapIfNonExists(Double mapX, Double mapY) {
        Optional<Map> optionalMap = mapRepository.findByMapXAndMapY(mapX, mapY);
        Map map;
        if (optionalMap.isPresent()) {
            map = optionalMap.get();

        } else {
            map = Map.builder()
                    .mapX(mapX)
                    .mapY(mapY)
                    .build();
            map = mapRepository.save(map);
        }
        return map;
    }


}
