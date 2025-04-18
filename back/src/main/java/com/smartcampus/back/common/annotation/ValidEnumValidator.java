package com.smartcampus.back.common.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @ValidEnum 어노테이션에 대한 검증 로직을 구현한 Validator입니다.
 *
 * 입력값이 enumClass에 정의된 값 중 하나인지 검사합니다.
 */
public class ValidEnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // null 값은 다른 @NotNull 등으로 검증하고 여기서는 true 처리
        if (value == null) return true;

        for (Enum<?> enumVal : enumClass.getEnumConstants()) {
            if (enumVal.name().equals(value)) {
                return true;
            }
        }

        return false;
    }
}
