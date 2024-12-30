package com.ourmenu.backend.domain.tag.application;

import com.ourmenu.backend.domain.tag.dao.MenuTagRepository;
import com.ourmenu.backend.domain.tag.dao.TagRepository;
import com.ourmenu.backend.domain.tag.domain.MenuTag;
import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuTagService {

    private final MenuTagRepository menuTagRepository;
    private final TagRepository tagRepository;

    /**
     * 태그 저장 및 연관관계 생성
     *
     * @param menuId
     * @param tagName
     * @return
     */
    @Transactional
    public Tag saveTag(Long menuId, String tagName) {
        Tag tag = saveTagIfNonExists(tagName);
        MenuTag menuTag = MenuTag.builder()
                .menuId(menuId)
                .tag(tag)
                .build();
        menuTagRepository.save(menuTag);
        return tag;
    }

    /**
     * 태그 확인 및 저장 (변수 이름 중복으로 tagName 한시적 사용
     *
     * @param tagName
     * @return
     */
    private Tag saveTagIfNonExists(String tagName) {
        Optional<Tag> optionalTag = tagRepository.findByTag(tagName);
        if (optionalTag.isPresent()) {
            return optionalTag.get();
        }

        Tag tag = Tag.builder()
                .tag(tagName)
                .build();
        return tagRepository.save(tag);
    }
}
