package com.ourmenu.backend.domain.user.application;

import com.ourmenu.backend.domain.user.dao.MealTimeRepository;
import com.ourmenu.backend.domain.user.dao.RefreshTokenRepository;
import com.ourmenu.backend.domain.user.dao.UserRepository;
import com.ourmenu.backend.domain.user.domain.*;
import com.ourmenu.backend.domain.user.dto.*;
import com.ourmenu.backend.global.util.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public String signUp(SignUpRequest signUpRequest) {

        if(userRepository.findByEmail(signUpRequest.getEmail()).isPresent()){
            throw new RuntimeException("같은 이메일이 존재합니다");
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
        mealTimeRepository.saveAll(mealTimes);


        return "OK";
    }

    /**
     * 로그인 로직 및 로그인 성공시 RefreshToken 갱신 후 JWT 정보 반환
     * @param signInRequest User의 Email, Password, SignInType정보를 가진 Request
     * @param response HTTP Response
     * @return Token 정보
     */
    public SignInResponse signIn(SignInRequest signInRequest, HttpServletResponse response) {

        User user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(
                () -> new RuntimeException("Not found Account")
        );

        if(!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Not matches Password");
        }

        SignInResponse tokenDto = jwtTokenProvider.createAllToken(signInRequest.getEmail());

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
    private void setHeader(HttpServletResponse response, SignInResponse tokenDto) {
        response.addHeader(JwtTokenProvider.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtTokenProvider.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    public String changePassword(PasswordRequest request, CustomUserDetails userDetails) {
        String rawPassword = request.password();
        String encodedPassword = userDetails.getPassword();

        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다"));
        String newPassword = passwordEncoder.encode(request.newPassword());
        user.changePassword(newPassword);
        userRepository.save(user);
        return "OK";
    }

    public String changeMealTime(MealTimeRequest request, CustomUserDetails userDetails) {
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

        mealTimeRepository.saveAll(updatedMealTimes);
        return "OK";
    }
}
