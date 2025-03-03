package com.smartcampus.back.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 친구 신고 요청 DTO
 * 사용자가 부적절한 활동을 하는 친구를 신고할 때 사용됨
 */
@Getter
@Setter
@NoArgsConstructor
public class FriendReportRequest {

    /**
     * 신고자 ID
     * 신고를하는 사용자의 ID
     */
    @NotNull(message = "신고자의 ID가 필요합니다.")
    private Long reporterId;

    /**
     * 신고 대상 친구 ID
     * 신고할 친구의 ID
     */
    @NotNull(message = "신고할 친구의 ID가 필요합니다.")
    private Long friendId;

    /**
     * 신고 사유
     * 사용자가 입력하거나 선택해야 하는 필수 값
     */
    @NotBlank(message = "신고 사유를 입력해야 합니다.")
    private String reason;

    /**
     * 추가 설명 (선택 사항)
     * 신고 사유에 대한 추가적인 설명
     */
    private String additionalDetails;
}
