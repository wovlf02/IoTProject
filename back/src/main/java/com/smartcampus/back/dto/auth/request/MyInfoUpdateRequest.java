package com.smartcampus.back.dto.auth.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 내 정보 수정 요청 DTO입니다.
 * 사용자는 닉네임과 비밀번호 중 원하는 항목을 수정할 수 있습니다.
 */
@Getter
@NoArgsConstructor
public class MyInfoUpdateRequest {

    /**
     * 변경할 닉네임 (선택)
     */
    @Size(min = 2, max = 30, message = "닉네임은 2자 이상 30자 이하로 입력해주세요.")
    private String nickname;

    /**
     * 변경할 비밀번호 (선택)
     */
    @Size(min = 6, max = 100, message = "비밀번호는 6자 이상 100자 이하로 입력해주세요.")
    private String password;

    /**
     * 생성자
     *
     * @param nickname 새 닉네임
     * @param password 새 비밀번호
     */
    public MyInfoUpdateRequest(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }
}
