package com.ourmenu.backend.domain.tag.application;

import com.ourmenu.backend.domain.tag.dao.MenuTagRepository;
import com.ourmenu.backend.domain.tag.domain.MenuTag;
import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuTagService {

    private final MenuTagRepository menuTagRepository;

    /**
     * 태그 저장 및 연관관계 생성
     *
     * @param menuId
     * @param tag
     * @return
     */
    @Transactional
    public Tag saveTag(Long menuId, Tag tag) {
        MenuTag menuTag = MenuTag.builder()
                .menuId(menuId)
                .tag(tag)
                .build();
        menuTagRepository.save(menuTag);
        return tag;
    }

    /**
     * menuTag 삭제
     *
     * @param menuId
     */
    @Transactional
    public void deleteMenuTag(Long menuId) {
        menuTagRepository.deleteAllByMenuId(menuId);
    }

    /**
     * 태그 조회
     *
     * @param menuId
     * @return
     */
    @Transactional
    public List<Tag> findTagNames(Long menuId) {
        return menuTagRepository.findMenuTagsByMenuId(menuId).stream()
                .map(menuTag -> menuTag.getTag())
                .toList();
    }
}
