package com.smartcampus.back.admin.dto.request;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

/**
 * 관리자 신고 처리 요청 DTO
 * <p>
 * 신고 처리 시 조치 유형(action)과 메모(memo)를 전달한다.
 * </p>
 */
@Getter
@Setter
public class UserResolveRequest {

    /**
     * 조치 유형 (예: HIDE, DELETE, SUSPEND 등)
     */
    @NotBlank(message = "조치 유형은 필수입니다.")
    private String action;

    /**
     * 처리 메모 (선택 사항, 사유 등 기록용)
     */
    private String memo;
}
