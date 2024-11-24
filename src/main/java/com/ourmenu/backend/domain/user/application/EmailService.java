package com.ourmenu.backend.domain.user.application;

import com.ourmenu.backend.domain.user.dto.EmailRequest;
import com.ourmenu.backend.domain.user.dto.EmailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;
    private final int CONFIRM_CODE_LENGTH = 6;

    public void sendEmail(String toEmail, String title, String content) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject(title);
        helper.setText(content, true); // true를 설정해서 HTML을 사용 가능하게 함
        helper.setReplyTo("ourmenuv2@gmail.com"); // 회신 불가능한 주소 설정
        try {
            emailSender.send(message);
        } catch (RuntimeException e) {
            e.printStackTrace(); // 또는 로거를 사용하여 상세한 예외 정보 로깅
            throw new RuntimeException("Unable to send email in sendEmail", e); // 원인 예외를 포함시키기
        }
    }

    public EmailResponse sendCodeToEmail(EmailRequest request) {
        String email = request.getEmail();
        String title = "아워메뉴 이메일 인증 번호";
        String generatedRandomCode = generateRandomCode(CONFIRM_CODE_LENGTH);

        String content = "<html>"
                + "<body>"
                + "<h1>아워메뉴 인증 코드: " + generatedRandomCode + "</h1>"
                + "</body>"
                + "</html>";

        try {
            sendEmail(email, title, content);
        } catch (RuntimeException | MessagingException e) {
            e.printStackTrace(); // 또는 로거를 사용하여 상세한 예외 정보 로깅
            throw new RuntimeException("Unable to send email in sendCodeToEmail", e); // 원인 예외를 포함시키기
        }

        EmailResponse response = EmailResponse.builder()
                .code(generatedRandomCode)
                .build();

        return response;
    }

    public String generateRandomCode(int length) {
        // 숫자 + 대문자
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder confirmCode = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            confirmCode.append(characters.charAt(index));
        }

        return confirmCode.toString();
    }

}
