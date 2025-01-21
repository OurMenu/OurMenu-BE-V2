package com.ourmenu.backend.domain.user.application;

import com.ourmenu.backend.domain.user.dao.MealTimeRepository;
import com.ourmenu.backend.domain.user.dao.RefreshTokenRepository;
import com.ourmenu.backend.domain.user.dao.UserRepository;
import com.ourmenu.backend.domain.user.domain.*;
import com.ourmenu.backend.domain.user.dto.request.MealTimeRequest;
import com.ourmenu.backend.domain.user.dto.request.PasswordRequest;
import com.ourmenu.backend.domain.user.dto.request.SignInRequest;
import com.ourmenu.backend.domain.user.dto.request.SignUpRequest;
import com.ourmenu.backend.domain.user.dto.response.ReissueToken;
import com.ourmenu.backend.domain.user.dto.response.TokenDto;
import com.ourmenu.backend.domain.user.dto.response.UserDto;
import com.ourmenu.backend.domain.user.exception.*;
import com.ourmenu.backend.global.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MealTimeRepository mealTimeRepository;


    /**
     * 이메일 중복 검사, 비밀번호 암호화 및 User 객체를 생성 후 DB에 저장
     * @param signUpRequest User의 Email, Password, SignInType, MealTime 정보를 가진 Request
     * @return 회원가입 완료
     */
    public void signUp(SignUpRequest signUpRequest) {

        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()){
            throw new DuplicateEmailException();
        }

        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());

        User user = User.builder()
                .email(signUpRequest.getEmail())
                .password(encodedPassword)
                .signInType(SignInType.valueOf(signUpRequest.getSignInType()))
                .build();

        User savedUser = userRepository.save(user);

        List<MealTime> mealTimes = new ArrayList<>();
        for (String mealTime : signUpRequest.getMealTime()) {
            MealTime newMealTime = MealTime.builder()
                    .userId(savedUser.getId())
                    .mealTime(mealTime)
                    .build();
            mealTimes.add(newMealTime);
        }

        if (mealTimes.isEmpty() || mealTimes.size() > 4){
            throw new InvalidMealTimeCountException();
        }

        mealTimeRepository.saveAll(mealTimes);

    }

    /**
     * 로그인 로직 및 로그인 성공시 RefreshToken 갱신 후 JWT 정보 반환
     * @param signInRequest User의 Email, Password, SignInType정보를 가진 Request
     * @param response HTTP Response
     * @return Token 정보
     */
    public TokenDto signIn(SignInRequest signInRequest, HttpServletResponse response) {

        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(
                UserNotFoundException::new
        );

        if(!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new NotMatchPasswordException();
        }

        TokenDto tokenDto = jwtTokenProvider.createAllToken(signInRequest.getEmail());

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findRefreshTokenByEmail(signInRequest.getEmail());

        if(refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        }else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), signInRequest.getEmail());
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);

        return tokenDto;
    }

    /**
     * Response Header에 AccessToken 값과 RefreshToken 값을 설정
     * @param response HTTP Response
     * @param tokenDto JWT Token 정보
     */
    private void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtTokenProvider.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtTokenProvider.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    public void changePassword(PasswordRequest request, CustomUserDetails userDetails) {
        String rawPassword = request.password();
        String encodedPassword = userDetails.getPassword();

        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new NotMatchPasswordException();
        }

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(UserNotFoundException::new);

        String newPassword = passwordEncoder.encode(request.newPassword());
        user.changePassword(newPassword);
        userRepository.save(user);
    }

    @Transactional
    public void changeMealTime(MealTimeRequest request, CustomUserDetails userDetails) {
        log.debug("{}", userDetails.getId());

        mealTimeRepository.deleteAllByUserId(userDetails.getId());

        ArrayList<String> newMealTimes = request.mealTime();

        ArrayList<MealTime> updatedMealTimes = new ArrayList<>();
        for (String mealTime : newMealTimes) {
            MealTime newMealTime = MealTime.builder()
                    .userId(userDetails.getId())
                    .mealTime(mealTime)
                    .build();
            updatedMealTimes.add(newMealTime);
        }

        if (updatedMealTimes.isEmpty() || updatedMealTimes.size() > 4){
            throw new InvalidMealTimeCountException();
        }

        mealTimeRepository.saveAll(updatedMealTimes);
    }

    public UserDto getUserInfo(CustomUserDetails userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(UserNotFoundException::new);

        return UserDto.of(user);
    }

    public TokenDto reissueToken(ReissueToken reissueToken) {
        String refreshToken = reissueToken.getRefreshToken();
        String email = jwtTokenProvider.getEmailFromToken(refreshToken);

        if (email.isEmpty()){
            throw new InvalidTokenException();
        }

        if (!jwtTokenProvider.tokenValidation(refreshToken)) {
            throw new TokenExpiredExcpetion();
        }

        RefreshToken storedToken = refreshTokenRepository.findRefreshTokenByEmail(email)
                .orElseThrow(NotMatchTokenException::new);

        String newAccessToken = jwtTokenProvider.createToken(email, "Access");
        String newRefreshToken = reissueToken.getRefreshToken();

        if (jwtTokenProvider.tokenValidation(refreshToken)) {
            newRefreshToken = jwtTokenProvider.createToken(email, "Refresh");
            storedToken.updateToken(newRefreshToken);
            refreshTokenRepository.save(storedToken);
        }

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .refreshTokenExpiredAt(jwtTokenProvider.getExpiredAt(newRefreshToken).toInstant())
                .build();
    }

    public void signOut(HttpServletRequest request, Long userId){
        String token = request.getHeader("Authorization");

        log.info("{}", token);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String email = jwtTokenProvider.getEmailFromToken(token);

            refreshTokenRepository.findRefreshTokenByEmail(email)
                    .ifPresent(refreshTokenRepository::delete);
        }
    }
}
