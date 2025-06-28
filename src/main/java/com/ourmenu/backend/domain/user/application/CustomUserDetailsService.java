package com.ourmenu.backend.domain.user.application;

import com.ourmenu.backend.domain.user.dao.UserRepository;
import com.ourmenu.backend.domain.user.domain.SignInType;
import com.ourmenu.backend.domain.user.domain.User;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService {

    private final UserRepository userRepository;

    public UserDetails loadUserByEmailAndSignInType(String email, SignInType signInType) throws UsernameNotFoundException {
        User user= userRepository.findByEmailAndSignInType(email, signInType).
                orElseThrow(NotFoundUserException::new);

        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword()
        );
    }

}
