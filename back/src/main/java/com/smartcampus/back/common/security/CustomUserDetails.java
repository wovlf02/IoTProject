package com.smartcampus.back.common.security;

import com.smartcampus.back.auth.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Spring Security의 인증된 사용자 정보 보관 클래스
 * <p>
 * 현재 로그인한 사용자의 인증 정보 및 권한을 SecurityContext에 저장합니다.
 */
@Getter
public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final String username;
    private final String password;

    public CustomUserDetails(User user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword(); // 주의: 암호화된 비밀번호
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 권한이 필요 없는 단순 시스템이면 빈 컬렉션 반환
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 → true면 사용 가능
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부
    }
}
