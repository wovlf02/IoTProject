package com.smartcampus.back.common.annotation;

import java.lang.annotation.*;

/**
 * 현재 로그인한 사용자를 컨트롤러 메서드의 인자로 주입하기 위한 커스텀 어노테이션입니다.
 *
 * 예시:
 * public ResponseEntity<?> getProfile(@CurrentUser User user) { ... }
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}
