package com.ourmenu.backend.domain.user.dao;

import com.ourmenu.backend.domain.user.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByEmail(String email);

}
