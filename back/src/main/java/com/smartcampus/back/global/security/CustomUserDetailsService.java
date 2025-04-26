package com.smartcampus.back.global.security;

import com.smartcampus.back.auth.entity.User;
import com.smartcampus.back.auth.repository.UserRepository;
import com.smartcampus.back.global.exception.CustomException;
import com.smartcampus.back.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * CustomUserDetailsService
 * <p>
 * Spring Security가 로그인 시 사용자를 인증하기 위해 UserDetails를 로드하는 서비스입니다.
 * DB에서 사용자 정보를 조회한 후 CustomUserDetails로 변환하여 반환합니다.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * username (로그인 ID)로 사용자 조회 후 UserDetails 반환
     *
     * @param username 로그인 ID
     * @return UserDetails 구현체 (CustomUserDetails)
     * @throws UsernameNotFoundException 사용자를 찾을 수 없는 경우 발생
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new CustomUserDetails(user);
    }
}
