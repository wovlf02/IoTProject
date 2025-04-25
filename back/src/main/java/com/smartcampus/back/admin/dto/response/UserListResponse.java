package com.smartcampus.back.admin.dto.response;

import com.smartcampus.back.auth.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 관리자 사용자 목록 응답 DTO
 * <p>
 * 사용자 리스트 + 페이징 메타 정보 포함
 * </p>
 */
@Getter
@Builder
public class UserListResponse {

    private List<UserSummary> users;      // 사용자 목록
    private int page;                     // 현재 페이지 번호
    private int size;                     // 페이지 크기
    private long totalElements;           // 전체 사용자 수
    private int totalPages;               // 전체 페이지 수

    /**
     * Page<User> 객체를 기반으로 DTO 변환
     */
    public static UserListResponse fromPage(Page<User> page) {
        return UserListResponse.builder()
                .users(page.getContent().stream()
                        .map(UserSummary::fromEntity)
                        .collect(Collectors.toList()))
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

    /**
     * 사용자 간략 정보 DTO
     */
    @Getter
    @Builder
    public static class UserSummary {
        private Long userId;
        private String username;
        private String email;
        private String nickname;
        private String status;
        private String createdAt;

        public static UserSummary fromEntity(User user) {
            return UserSummary.builder()
                    .userId(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .status(user.getStatus().name())
                    .createdAt(user.getCreatedAt().toString())
                    .build();
        }
    }
}
