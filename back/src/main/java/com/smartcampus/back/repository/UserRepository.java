package com.smartcampus.back.repository;

import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository (사용자 Repository)
 * users 테이블과 매핑됨
 * 사용자 정보 조회, 저장, 삭제 등의 기능 제공
 * 로그인 및 인증 관련 데이터 접근 수행
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 사용자 아이디(username)로 조회
     * @param username 찾을 사용자 아이디
     * @return Optional<User> (사용자 정보)
     */
    Optional<User> findByUsername(String username);

    /**
     * 사용자 이메일(email)로 조회
     * @param email 찾을 이메일
     * @return Optional<User> (사용자 정보)
     */
    Optional<User> findByEmail(String email);

    /**
     * 닉네임 중복 여부 확인
     * @param nickname 검사할 닉네임
     * @return 존재하면 true, 존재하지 않으면 false
     */
    boolean existsByNickname(String nickname);

    /**
     * 이메일 중복 여부 확인
     * @param email 검사할 이메일
     * @return 존재하면 true, 존재하지 않으면 false
     */
    boolean existsByEmail(String email);

    /**
     * 아이디(username) 중복 여부 확인
     * @param username 검사할 아이디
     * @return 존재하면 true, 존재하지 않으면 false
     */
    boolean existsByUsername(String username);
}
