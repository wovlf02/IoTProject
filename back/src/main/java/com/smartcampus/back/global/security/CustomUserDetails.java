package com.smartcampus.back.global.security;

import com.smartcampus.back.auth.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * CustomUserDetails
 * <p>
 * 로그인한 사용자의 정보를 담는 UserDetails 구현체입니다.
 * User 엔티티를 그대로 들고 있으면서, 필요한 데이터(username, password, role 등)를 제공합니다.
 * </p>
 */
@Getter
public class CustomUserDetails implements UserDetails {

    private final User user; // 🔥 User 전체를 들고 있게 수정

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_" + user.getRole().name());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 정책 없음
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 정책 없음
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 정책 없음
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus() == User.Status.ACTIVE; // 계정 상태 ACTIVE 여부로 사용 가능 여부 판단
    }
}
