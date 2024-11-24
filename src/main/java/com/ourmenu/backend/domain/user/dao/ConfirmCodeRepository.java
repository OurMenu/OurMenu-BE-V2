package com.ourmenu.backend.domain.user.dao;

import com.ourmenu.backend.domain.user.domain.ConfirmCode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ConfirmCodeRepository extends CrudRepository<ConfirmCode, Long> {
    Optional<ConfirmCode> findConfirmCodeByEmail(String email);
}
