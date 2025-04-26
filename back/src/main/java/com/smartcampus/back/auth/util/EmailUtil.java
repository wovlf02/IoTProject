package com.smartcampus.back.auth.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * EmailUtil
 * <p>
 * 이메일 인증코드를 생성하고 발송하는 유틸리티 클래스입니다.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail; // 발신자 이메일 주소

    @Value("${spring.mail.sender-name:SmartCampus}")
    private String senderName; // 발신자 이름 (기본값 SmartCampus)

    private static final int CODE_LENGTH = 6;

    /**
     * 6자리 인증코드를 생성합니다.
     *
     * @return 인증코드 (숫자 문자열)
     */
    public String createAuthCode() {
        Random random = new Random();
        StringBuilder authCode = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            authCode.append(random.nextInt(10)); // 0~9 랜덤 숫자
        }

        return authCode.toString();
    }

    /**
     * 인증코드 이메일을 발송합니다.
     *
     * @param toEmail 수신자 이메일 주소
     * @param authCode 발송할 인증코드
     */
    public void sendAuthCode(String toEmail, String authCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, senderName);
            helper.setTo(toEmail);
            helper.setSubject("[SmartCampus] 이메일 인증 코드 안내");

            String content = "<p>SmartCampus를 이용해주셔서 감사합니다.</p>" +
                    "<p>아래 인증코드를 입력해 주세요.</p>" +
                    "<h2>" + authCode + "</h2>";

            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("이메일 발송 중 오류가 발생했습니다.", e);
        }
    }
}
