package com.studymate.back.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 친구 응답 DTO
 * 친구 목록 조회 및 친구 요청
 */
@Getter
@Setter
@AllArgsConstructor
public class FriendResponse {

    /**
     * 친구 관계 ID
     * 데이터베이스에서 자동 생성됨
     */
    private Long id;

    /**
     * 친구 사용자 ID
     * 친구의 ID
     */
    private Long friendId;

    /**
     * 친구 닉네임
     * 친구의 닉네임 또는 이름
     */
    private String friendName;

    /**
     * 친구 프로필 이미지 URL
     * 친구의 프로필 사진 경로
     */
    private String profileImageUrl;

    /**
     * 친구 추가 날짜
     * 친구 관계가 형성된 날짜 및 시간
     */
    private LocalDateTime createdAt;
}
