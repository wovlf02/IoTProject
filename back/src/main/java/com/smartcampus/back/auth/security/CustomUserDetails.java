package com.smartcampus.back.auth.security;

import com.smartcampus.back.auth.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * CustomUserDetails
 * <p>
 * 로그인한 사용자의 정보를 저장하는 UserDetails 구현체입니다.
 * User 엔티티 전체를 들고 있으며, Security 인증 및 인가에 필요한 정보를 제공합니다.
 * </p>
 */
@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    /**
     * User 엔티티를 기반으로 CustomUserDetails 객체 생성
     *
     * @param user User 엔티티
     */
    public CustomUserDetails(User user) {
        this.user = user;
    }

    /**
     * 사용자의 권한 목록을 반환합니다.
     *
     * @return ROLE_USER 또는 ROLE_ADMIN
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_" + user.getRole().name());
    }

    /**
     * 사용자 비밀번호 반환
     *
     * @return 암호화된 비밀번호
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 사용자 ID(username) 반환
     *
     * @return 로그인 ID (username)
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 계정 만료 여부
     *
     * @return true (만료 정책 없음)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정 잠김 여부
     *
     * @return true (잠김 정책 없음)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 자격 증명(비밀번호) 만료 여부
     *
     * @return true (비밀번호 만료 정책 없음)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 사용자 활성화 여부
     *
     * @return true = 활성 상태 (ACTIVE), false = 비활성화 (SUSPENDED, WITHDRAWN)
     */
    @Override
    public boolean isEnabled() {
        return user.getStatus() == User.Status.ACTIVE;
    }
}
