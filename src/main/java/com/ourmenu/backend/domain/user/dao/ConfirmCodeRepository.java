package com.ourmenu.backend.domain.user.dao;

import com.ourmenu.backend.domain.user.domain.ConfirmCode;
import org.springframework.data.repository.CrudRepository;

public interface ConfirmCodeRepository extends CrudRepository<ConfirmCode, Long> {
}
