package com.ourmenu.backend.domain.user.dao;

import com.ourmenu.backend.domain.user.domain.RefreshToken;
import com.ourmenu.backend.domain.user.domain.SignInType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

    Optional<RefreshToken> findRefreshTokenByEmailAndSignInType(String email, SignInType signInType);

}
