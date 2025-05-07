package com.smartcampus.back.repository;

import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 사용자 엔티티에 대한 데이터 접근을 처리하는 JPA Repository입니다.
 * 회원가입, 로그인, 중복 확인 등의 기능에 활용됩니다.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 아이디로 사용자 조회
     *
     * @param username 사용자 아이디
     * @return 해당 아이디의 사용자 Optional
     */
    Optional<User> findByUsername(String username);

    /**
     * 닉네임으로 사용자 조회
     *
     * @param nickname 사용자 닉네임
     * @return 해당 닉네임의 사용자 Optional
     */
    Optional<User> findByNickname(String nickname);

    /**
     * 이메일로 사용자 조회
     *
     * @param email 사용자 이메일
     * @return 해당 이메일의 사용자 Optional
     */
    Optional<User> findByEmail(String email);

    /**
     * 로그인용: 아이디와 계정 상태로 사용자 조회
     *
     * @param username 사용자 아이디
     * @param status 계정 상태 (예: ACTIVE)
     * @return 일치하는 사용자 Optional
     */
    Optional<User> findByUsernameAndStatus(String username, String status);
}
