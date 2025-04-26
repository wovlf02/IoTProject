package com.smartcampus.back.auth.repository;

import com.smartcampus.back.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository
 * <p>
 * User 엔티티에 대한 데이터베이스 접근을 담당하는 JpaRepository 인터페이스입니다.
 * username, email, nickname 등을 기준으로 사용자 조회 기능을 제공합니다.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * username으로 사용자 조회
     *
     * @param username 로그인 ID
     * @return Optional<User>
     */
    Optional<User> findByUsername(String username);

    /**
     * nickname으로 사용자 조회
     *
     * @param nickname 사용자 닉네임
     * @return Optional<User>
     */
    Optional<User> findByNickname(String nickname);

    /**
     * email로 사용자 조회
     *
     * @param email 사용자 이메일
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * username 중복 여부 체크
     *
     * @param username 로그인 ID
     * @return true = 이미 존재
     */
    boolean existsByUsername(String username);

    /**
     * nickname 중복 여부 체크
     *
     * @param nickname 사용자 닉네임
     * @return true = 이미 존재
     */
    boolean existsByNickname(String nickname);

    /**
     * email 중복 여부 체크
     *
     * @param email 사용자 이메일
     * @return true = 이미 존재
     */
    boolean existsByEmail(String email);
}
