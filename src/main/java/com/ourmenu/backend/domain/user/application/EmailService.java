package com.ourmenu.backend.domain.user.application;

import com.ourmenu.backend.domain.user.dao.ConfirmCodeRepository;
import com.ourmenu.backend.domain.user.domain.ConfirmCode;
import com.ourmenu.backend.domain.user.dto.request.EmailRequest;
import com.ourmenu.backend.domain.user.dto.response.EmailResponse;
import com.ourmenu.backend.domain.user.dto.request.VerifyEmailRequest;
import com.ourmenu.backend.domain.user.exception.ConfirmCodeNotFoundException;
import com.ourmenu.backend.domain.user.exception.NotMatchConfirmCodeException;
import com.ourmenu.backend.domain.user.exception.SendCodeFailureException;
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
    private final ConfirmCodeRepository confirmCodeRepository;

    public void sendEmail(String toEmail, String title, String content) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject(title);
        helper.setText(content, true);
        helper.setReplyTo("ourmenuv2@gmail.com");
        emailSender.send(message);
    }

    public EmailResponse sendCodeToEmail(EmailRequest request){
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
        }catch (MessagingException e){
            throw new SendCodeFailureException();
        }

        ConfirmCode confirmCode = ConfirmCode.of(email, generatedRandomCode);
        confirmCodeRepository.save(confirmCode);

        return EmailResponse.builder()
                .code(generatedRandomCode)
                .build();
    }

    public String generateRandomCode(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder confirmCode = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            confirmCode.append(characters.charAt(index));
        }

        return confirmCode.toString();
    }

    public String verifyConfirmCode(VerifyEmailRequest request){
        String email = request.getEmail();
        String inputConfirmCode = request.getConfirmCode();

        log.error("{},{}", email, inputConfirmCode);

        ConfirmCode confirmCode = confirmCodeRepository.findConfirmCodeByEmail(email)
                .orElseThrow(ConfirmCodeNotFoundException::new);

        if (!confirmCode.getConfirmCode().equals(inputConfirmCode)) {
            throw new NotMatchConfirmCodeException();
        }

        return "OK";
    }

}
