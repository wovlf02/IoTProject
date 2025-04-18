package com.smartcampus.back.common.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Enum 값 유효성 검증을 위한 커스텀 어노테이션입니다.
 *
 * 사용 예시:
 * <pre>
 *     @ValidEnum(enumClass = RoomType.class)
 *     private String roomType;
 * </pre>
 */
@Documented
@Constraint(validatedBy = ValidEnumValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {

    /**
     * 대상 Enum 클래스
     */
    Class<? extends Enum<?>> enumClass();

    /**
     * 에러 메시지 (기본값 제공 가능)
     */
    String message() default "유효하지 않은 값입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
