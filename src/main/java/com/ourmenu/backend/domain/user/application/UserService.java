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
     * 이메일 중복 검사, 비밀번호 암호화 및 User 객체를 생성 후 DB에 저장
     *
     * @param request User의 Email, Password, SignInType, MealTime 정보를 가진 Request
     * @return 회원가입 완료
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
     * 로그인 로직 및 로그인 성공시 RefreshToken 갱신 후 JWT 정보 반환
     *
     * @param emailSignInRequest User의 Email, Password, SignInType정보를 가진 Request
     * @param response           HTTP Response
     * @return Token 정보
     */
    public TokenDto signIn(EmailSignInRequest emailSignInRequest, HttpServletResponse response) {

        User user = userRepository.findByEmail(emailSignInRequest.getEmail()).orElseThrow(
                NotFoundUserException::new
        );

        if (!passwordEncoder.matches(emailSignInRequest.getPassword(), user.getPassword())) {
            throw new NotMatchPasswordException();
        }

        TokenDto tokenDto = jwtTokenProvider.createAllToken(emailSignInRequest.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findRefreshTokenByEmail(
                emailSignInRequest.getEmail());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), emailSignInRequest.getEmail());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);

        return tokenDto;
    }

    /**
     * Response Header에 AccessToken 값 반환
     *
     * @param response HTTP Response
     * @param tokenDto JWT Token 정보
     */
    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtTokenProvider.ACCESS_TOKEN, tokenDto.getAccessToken());
    }

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

    public UserDto getUserInfo(CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(NotFoundUserException::new);

        List<MealTime> mealTimes = mealTimeService.findAllByUserId(userDetails.getId());

        return UserDto.of(user, mealTimes);
    }

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

    public void signOut(HttpServletRequest request, Long userId) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String email = jwtTokenProvider.getEmailFromToken(token);

            refreshTokenRepository.findRefreshTokenByEmail(email)
                    .ifPresent(refreshTokenRepository::delete);
        }
    }

    public KakaoExistenceResponse validateKakaoUserExists(EmailRequest request) {
        String email = request.getEmail();

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent() && optionalUser.get().getSignInType() == SignInType.KAKAO) {
            return KakaoExistenceResponse.from(true);
        }

        return KakaoExistenceResponse.from(false);
    }

    private User saveUser(EmailSignUpRequest request) {
        if (request.getSignInType().equals("EMAIL")) {
            return signUpByEmail(request);
        }

        if (request.getSignInType().equals("KAKAO")) {
            return signUpByKakao(request);
        }

        throw new UnsupportedSignInTypeException();
    }

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
