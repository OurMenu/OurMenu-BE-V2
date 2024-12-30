package com.ourmenu.backend.domain.tag.dao;

import com.ourmenu.backend.domain.tag.domain.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTag(String tag);
}
