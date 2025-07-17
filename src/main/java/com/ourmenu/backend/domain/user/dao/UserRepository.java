package com.ourmenu.backend.domain.user.dao;

import com.ourmenu.backend.domain.user.domain.SignInType;
import com.ourmenu.backend.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndSignInType(String email, SignInType signInType);
}
