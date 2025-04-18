package com.smartcampus.back.auth.service;

import com.smartcampus.back.auth.dto.request.EmailVerificationRequest;
import com.smartcampus.back.auth.dto.response.EmailVerificationResponse;
import com.smartcampus.back.auth.dto.response.VerifyEmailResponse;
import com.smartcampus.back.auth.util.AuthCodeGenerator;
import com.smartcampus.back.auth.util.EmailUtil;
import com.smartcampus.back.common.exception.CustomException;
import com.smartcampus.back.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 이메일 인증 처리 서비스
 *
 * 인증 코드 생성 및 이메일 전송
 * 인증 코드 검증
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private static final long EXPIRATION_MINUTES = 5; // 인증번호 유효 시간

    private final EmailUtil emailUtil;
    private final AuthCodeGenerator codeGenerator;
    private final StringRedisTemplate redisTemplate;

    /**
     * 이메일로 인증 코드를 전송
     * @param request 이메일 인증 요청 DTO
     * @return 전송 완료 메시지
     */
    public EmailVerificationResponse sendVerificationCode(EmailVerificationRequest request) {
        String email = request.getEmail();
        String code = codeGenerator.generate6DigitCode();

        // Redis에 저장 (5분간 유효)
        redisTemplate.opsForValue().set(email, code, EXPIRATION_MINUTES, TimeUnit.MINUTES);

        // 이메일 발송
        emailUtil.sendVerificationEmail(email, code);

        return new EmailVerificationResponse("인증 코드가 이메일로 전송되었습니다.");
    }

    /**
     * 사용자가 제출한 인증 코드 검증
     *
     * @param request 이메일 + 인증 코드
     * @return 인증 성공 여부
     */
    public VerifyEmailResponse verifyCode(EmailVerificationRequest request) {
        String email = request.getEmail();
        String inputCode = request.getCode();

        String savedCode = redisTemplate.opsForValue().get(email);

        if(savedCode == null || !savedCode.equals(inputCode)) {
            throw new CustomException(ErrorCode.INVALID_AUTH_CODE);
        }

        // 인증 성공 -> Redis에서 삭제
        redisTemplate.delete(email);

        return new VerifyEmailResponse(true);
    }
}
