package com.smartcampus.back.global.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * 이메일 발송 유틸리티
 * <p>
 * 인증코드 발송 등을 위해 SMTP를 통해 이메일을 전송합니다.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail; // 발신자 이메일 주소 (네이버 계정)

    @Value("${spring.mail.sender-name:SmartCampus}")
    private String senderName; // 발신자 이름 (기본: SmartCampus)

    /**
     * 이메일로 인증코드 발송
     *
     * @param toEmail 수신자 이메일 주소
     * @param code 인증코드 (6자리)
     */
    public void sendCode(String toEmail, String code) {
        String subject = "[SmartCampus] 이메일 인증코드 안내";
        String text = """
                안녕하세요, SmartCampus입니다.

                요청하신 이메일 인증코드는 아래와 같습니다.

                인증코드: %s

                인증코드는 5분간 유효합니다.
                
                감사합니다.
                """.formatted(code);

        sendEmail(toEmail, subject, text);
    }

    /**
     * 실제 이메일 전송
     *
     * @param toEmail 수신자 이메일
     * @param subject 제목
     * @param text 본문 내용
     */
    private void sendEmail(String toEmail, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            helper.setFrom(fromEmail, senderName); // 네이버 SMTP는 발신자 이름 설정 가능
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(text, false); // HTML 여부: false (텍스트)

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송에 실패했습니다.", e);
        } catch (Exception e) {
            throw new RuntimeException("이메일 설정 오류", e);
        }
    }
}
