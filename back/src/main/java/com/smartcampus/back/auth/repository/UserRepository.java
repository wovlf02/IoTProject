package com.smartcampus.back.auth.repository;

import com.smartcampus.back.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User 엔티티에 대한 데이터 액세스를 담당하는 JPA 리포지토리
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 아이디로 사용자 조회
     *
     * @param username 아이디
     * @return Optional<User>
     */
    Optional<User> findByUsername(String username);

    /**
     * 이메일로 사용자 조회
     *
     * @param email 이메일
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * 닉네임으로 사용자 조회
     *
     * @param nickname 닉네임
     * @return Optional<User>
     */
    Optional<User> findByNickname(String nickname);

    /**
     * 아이디 존재 여부 확인
     *
     * @param username 아이디
     * @return true: 존재함, false: 존재하지 않음
     */
    boolean existsByUsername(String username);

    /**
     * 이메일 존재 여부 확인
     *
     * @param email 이메일
     * @return true: 존재함, false: 존재하지 않음
     */
    boolean existsByEmail(String email);

    /**
     * 닉네임 존재 여부 확인
     *
     * @param nickname 닉네임
     * @return true: 존재함, false: 존재하지 않음
     */
    boolean existsByNickname(String nickname);
}
