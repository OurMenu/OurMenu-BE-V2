package com.ourmenu.backend.domain.user.application;

import com.ourmenu.backend.domain.user.dao.RefreshTokenRepository;
import com.ourmenu.backend.domain.user.dao.UserRepository;
import com.ourmenu.backend.domain.user.domain.CustomUserDetails;
import com.ourmenu.backend.domain.user.domain.MealTime;
import com.ourmenu.backend.domain.user.domain.RefreshToken;
import com.ourmenu.backend.domain.user.domain.SignInType;
import com.ourmenu.backend.domain.user.domain.User;
import com.ourmenu.backend.domain.user.dto.request.EmailRequest;
import com.ourmenu.backend.domain.user.dto.request.EmailSignInRequest;
import com.ourmenu.backend.domain.user.dto.request.EmailSignUpRequest;
import com.ourmenu.backend.domain.user.dto.request.PasswordRequest;
import com.ourmenu.backend.domain.user.dto.response.KakaoExistenceResponse;
import com.ourmenu.backend.domain.user.dto.response.ReissueRequest;
import com.ourmenu.backend.domain.user.dto.response.TokenDto;
import com.ourmenu.backend.domain.user.dto.response.UserDto;
import com.ourmenu.backend.domain.user.exception.DuplicateEmailException;
import com.ourmenu.backend.domain.user.exception.InvalidMealTimeCountException;
import com.ourmenu.backend.domain.user.exception.InvalidTokenException;
import com.ourmenu.backend.domain.user.exception.NotFoundUserException;
import com.ourmenu.backend.domain.user.exception.NotMatchPasswordException;
import com.ourmenu.backend.domain.user.exception.NotMatchTokenException;
import com.ourmenu.backend.domain.user.exception.TokenExpiredExcpetion;
import com.ourmenu.backend.domain.user.exception.UnsupportedSignInTypeException;
import com.ourmenu.backend.global.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MealTimeService mealTimeService;


    /**
     * 이메일 중복 검사, 비밀번호 암호화 및 User 객체를 생성 후 DB에 저장한다
     *
     * @param request User의 Email, Password, SignInType, MealTime Request
     */
    @Transactional
    public void signUp(EmailSignUpRequest request) {

        User savedUser = saveUser(request);

        List<MealTime> mealTimes = mealTimeService.saveMealTimes(request.getMealTime(), savedUser.getId());

        if (mealTimes.isEmpty() || mealTimes.size() > 4) {
            userRepository.delete(savedUser);
            throw new InvalidMealTimeCountException();
        }
    }

    /**
     * 로그인 로직 및 로그인 성공시 RefreshToken 갱신 후 JWT 정보 반환한다
     *
     * @param request    User의 Email, Password, SignInType Request
     * @param response           HTTP Response
     * @return Token 정보
     */
    public TokenDto signIn(EmailSignInRequest request, HttpServletResponse response) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isEmpty() || !optionalUser.get().getSignInType().name().equals(request.getSignInType())) {
            throw new NotFoundUserException();
        }

        User user = optionalUser.get();

        if (request.getSignInType().equals("EMAIL") && !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new NotMatchPasswordException();
        }

        TokenDto tokenDto = jwtTokenProvider.createAllToken(request.getEmail());
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findRefreshTokenByEmail(request.getEmail());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), request.getEmail());
            refreshTokenRepository.save(newToken);
        }

        return tokenDto;
    }

    /**
     * 비밀번호를 변경한다.
     *
     * @param request
     * @param userDetails
     */
    public void changePassword(PasswordRequest request, CustomUserDetails userDetails) {
        String rawPassword = request.getPassword();
        String encodedPassword = userDetails.getPassword();

        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new NotMatchPasswordException();
        }

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(NotFoundUserException::new);

        String newPassword = passwordEncoder.encode(request.getNewPassword());
        user.changePassword(newPassword);
        userRepository.save(user);
    }

    /**
     * 유저 정보를 조회한다.
     *
     * @param userDetails
     * @return
     */
    public UserDto getUserInfo(CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(NotFoundUserException::new);

        List<MealTime> mealTimes = mealTimeService.findAllByUserId(userDetails.getId());

        return UserDto.of(user, mealTimes);
    }

    /**
     * RefreshToken을 사용해 토큰을 갱신한다.
     *
     * @param reissueRequest
     * @return
     */
    public TokenDto reissueToken(ReissueRequest reissueRequest) {
        String refreshToken = reissueRequest.getRefreshToken();
        String email = jwtTokenProvider.getEmailFromToken(refreshToken);

        if (email.isEmpty()) {
            throw new InvalidTokenException();
        }

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new TokenExpiredExcpetion();
        }

        RefreshToken storedToken = refreshTokenRepository.findRefreshTokenByEmail(email)
                .orElseThrow(NotMatchTokenException::new);

        String newAccessToken = jwtTokenProvider.createToken(email, "Access");
        String newRefreshToken = reissueRequest.getRefreshToken();

        if (jwtTokenProvider.validateToken(refreshToken)) {
            newRefreshToken = jwtTokenProvider.createToken(email, "Refresh");
            storedToken.updateToken(newRefreshToken);
            refreshTokenRepository.save(storedToken);
        }

        return TokenDto.of(newAccessToken,
                newRefreshToken,
                jwtTokenProvider.getExpiredAt(newRefreshToken).toInstant()
        );
    }

    /**
     * 해당 유저의 RefreshToken을 제거하며 로그아웃한다.
     *
     * @param request
     * @param userId
     */
    public void signOut(HttpServletRequest request, Long userId) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String email = jwtTokenProvider.getEmailFromToken(token);

            refreshTokenRepository.findRefreshTokenByEmail(email)
                    .ifPresent(refreshTokenRepository::delete);
        }
    }

    /**
     * 카카오 계정 존재 여부를 검증한다.
     *
     * @param request
     * @return
     */
    public KakaoExistenceResponse validateKakaoUserExists(EmailRequest request) {
        String email = request.getEmail();

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent() && optionalUser.get().getSignInType() == SignInType.KAKAO) {
            return KakaoExistenceResponse.from(true);
        }

        return KakaoExistenceResponse.from(false);
    }

    /**
     * 유저 정보를 저장한다.
     *
     * @param request
     * @return
     * @throws UnsupportedSignInTypeException 지원하지 않는 SignInType을 요청한 경우
     */
    private User saveUser(EmailSignUpRequest request) {
        if (request.getSignInType().equals("EMAIL")) {
            return signUpByEmail(request);
        }

        if (request.getSignInType().equals("KAKAO")) {
            return signUpByKakao(request);
        }

        throw new UnsupportedSignInTypeException();
    }

    /**
     * Kakao 유저를 저장한다.
     *
     * @param request
     * @return
     */
    private User signUpByKakao(EmailSignUpRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());
        if (optionalUser.isPresent() && optionalUser.get().getSignInType() == SignInType.KAKAO) {
            throw new DuplicateEmailException();
        }

        User user = User.builder()
                .email(request.getEmail())
                .signInType(SignInType.KAKAO)
                .build();

        return userRepository.save(user);
    }

    /**
     * Email 유저를 저장한다.
     *
     * @param request
     * @return
     */
    private User signUpByEmail(EmailSignUpRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .signInType(SignInType.EMAIL)
                .build();
        return userRepository.save(user);
    }
}
