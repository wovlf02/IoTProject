package com.smartcampus.back.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 시간표 교시 등록용 DTO입니다.
 * 사용자가 입력한 교시 정보를 기반으로 시간표를 구성합니다.
 */
@Getter
@NoArgsConstructor
public class TimetableSlotDto {

    /**
     * 요일 (예: MON, TUE, WED ...)
     */
    @NotBlank(message = "요일은 필수입니다.")
    private String dayOfWeek;

    /**
     * 교시 번호 (1, 2, 3 ...)
     */
    @NotNull(message = "교시 번호는 필수입니다.")
    private Integer period;

    /**
     * 과목명
     */
    @NotBlank(message = "과목명은 필수입니다.")
    private String subjectName;

    /**
     * 교수명 (선택)
     */
    private String professor;

    /**
     * 건물명 (선택)
     */
    private String building;

    /**
     * 강의실 (선택)
     */
    private String room;

    /**
     * 수업 시작 시각 (예: "09:00")
     */
    @NotBlank(message = "시작 시각은 필수입니다.")
    private String startTime;

    /**
     * 수업 종료 시각 (예: "10:15")
     */
    @NotBlank(message = "종료 시각은 필수입니다.")
    private String endTime;

    /**
     * 주차 구분 (ALL, ODD, EVEN 등 - 선택)
     */
    private String weekType;

    /**
     * 메모 (선택)
     */
    private String memo;
}
