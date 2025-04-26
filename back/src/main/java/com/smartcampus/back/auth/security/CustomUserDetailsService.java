package com.smartcampus.back.auth.security;

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
 * Spring Security가 로그인 과정에서 사용자 정보를 조회하기 위해 사용하는 서비스입니다.
 * username으로 User 엔티티를 조회하고, CustomUserDetails로 변환하여 반환합니다.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * username(아이디)로 사용자 정보를 조회하여 UserDetails를 반환합니다.
     *
     * @param username 로그인 요청 시 입력한 아이디
     * @return CustomUserDetails (Spring Security 인증용 객체)
     * @throws UsernameNotFoundException 사용자를 찾을 수 없는 경우
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new CustomUserDetails(user);
    }
}
