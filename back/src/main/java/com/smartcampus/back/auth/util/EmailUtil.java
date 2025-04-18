package com.smartcampus.back.auth.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

/**
 * 이메일 전송 유틸리티 클래스
 *
 * JavaMailSender를통해 인증 코드 메일을 HTML 포맷으로 전송
 */
@Component
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender mailSender;

    @Value("${spirng.mail.username}")
    private String fromEmail;

    @Value("${spring.mail.sender-name:SmartCampus}")
    private String senderName;

    /**
     * 이메일로 인증 코드 전송
     * @param to 수신자 이메일 주소
     * @param code 인증 코드 (6자리 숫자)
     */
    public void sendVerificationEmail(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail, senderName);
            helper.setTo(to);
            helper.setSubject("[SmartCampus] 이메일 인증 코드 안내");

            String content = buildHtmlContent(code);
            helper.setText(content, true);
            mailSender.send(message);
        } catch(MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("이메일 전송에 실패했습니다.", e);
        }
    }

    /**
     * 인증 코드가 포함된 HTML 메일 본문 생성
     *
     * @param code 6자리 인증 코드
     * @return HTML 문자열
     */
    private String buildHtmlContent(String code) {
        return "<div style='font-family:Arial, sans-serif; padding:20px;'>"
                + "<h2>HamCam 이메일 인증</h2>"
                + "<p>아래의 인증 코드를 입력해주세요:</p>"
                + "<div style='font-size:24px; font-weight:bold; margin:20px 0; color:#007bff;'>"
                + code
                + "</div>"
                + "<p>인증 코드는 5분간 유효합니다.</p>"
                + "</div>";
    }
}
