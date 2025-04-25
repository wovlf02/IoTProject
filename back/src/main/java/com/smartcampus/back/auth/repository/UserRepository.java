package com.smartcampus.back.auth.repository;

import com.smartcampus.back.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 사용자(User) 엔티티 전용 Repository
 * <p>
 * 로그인, 회원가입, 계정 복구, 사용자 상태 관리 기능을 지원합니다.
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 아이디(username)로 사용자 조회
     *
     * @param username 아이디
     * @return 사용자 Optional
     */
    Optional<User> findByUsername(String username);

    /**
     * 이메일로 사용자 조회
     *
     * @param email 이메일 주소
     * @return 사용자 Optional
     */
    Optional<User> findByEmail(String email);

    /**
     * 닉네임으로 사용자 조회
     *
     * @param nickname 닉네임
     * @return 사용자 Optional
     */
    Optional<User> findByNickname(String nickname);

    /**
     * username과 email을 동시에 만족하는 사용자 조회
     * (비밀번호 재설정 본인확인용)
     *
     * @param username 아이디
     * @param email 이메일 주소
     * @return 사용자 Optional
     */
    Optional<User> findByUsernameAndEmail(String username, String email);

    /**
     * 해당 username이 존재하는지 여부
     *
     * @param username 아이디
     * @return 존재 여부 (true/false)
     */
    boolean existsByUsername(String username);

    /**
     * 해당 이메일이 존재하는지 여부
     *
     * @param email 이메일
     * @return 존재 여부 (true/false)
     */
    boolean existsByEmail(String email);

    /**
     * 해당 닉네임이 존재하는지 여부
     *
     * @param nickname 닉네임
     * @return 존재 여부 (true/false)
     */
    boolean existsByNickname(String nickname);
}
