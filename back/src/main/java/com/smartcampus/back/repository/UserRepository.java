package com.smartcampus.back.repository;

import com.smartcampus.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository 인터페이스
 *
 * users 테이블과 매핑된 User 엔티티의 데이터 접근 계층
 * 회원 정보 조회, 회원가입, 이메일 및 아이디 중복 체크, 인증 관련 기능 포함
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 아이디(username)로 사용자 찾기
     *
     * 로그인 시 아이디 기반으로 사용자 정보를 조회
     * 결과가 없을 경우 Optional.empty() 반환
     *
     * @param username 사용자의 아이디
     * @return Optional<User> 객체 (사용자가 존재하면 User, 없으면 empty)
     */
    Optional<User> findByUsername(String username);

    /**
     * 이메일(email)로 사용자 찾기
     *
     * 회원가입 또는 비밀번호 찾기 시 이메일 기반 조회
     * 결과가 없을 경우 Optional.empty() 반환
     *
     * @param email 사용자의 이메일
     * @return Optional<User> 객체 (사용자가 존재하면 User, 없으면 empty)
     */
    Optional<User> findByEmail(String email);

    /**
     * 닉네임(nickname)으로 사용자 찾기
     *
     * 회원가입 시 닉네임 중복 여부 확인
     * 결과가 없을 경우 Optional.empty() 반환
     *
     * @param nickname 사용자의 닉네임
     * @return Optional<User> 객체 (사용자가 존재하면 User, 없으면 empty)
     */
    Optional<User> findByNickname(String nickname);

    /**
     * 아이디(username) 중복 확인
     *
     * 회원가입 시 아이디 중복 여부를 확인하기 위해 사용
     * 존재하는 경우 true 반환
     *
     * @param username 중복 확인할 사용자 아이디
     * @return boolean (아이디가 존재하면 true, 없으면 false)
     */
    boolean existsByUsername(String username);

    /**
     * 이메일(email) 중복 확인
     *
     * 회원가입 시 이메일 중복 여부를 확인하기 위해 사용
     * 존재하는 경우 true 반환
     *
     * @param email 중복 확인할 사용자 이메일
     * @return boolean (이메일이 존재하면 true, 없으면 false)
     */
    boolean existsByEmail(String email);

    /**
     * 닉네임(nickname) 중복 확인
     *
     * 회원가입 시 닉네임 중복 여부를 확인하기 위해 사용
     * 존재하는 경우 true 반환
     *
     * @param nickname 중복 확인할 사용자 닉네임
     * @return boolean (닉네임이 존재하면 true, 없으면 false)
     */
    boolean existsByNickname(String nickname);

    /**
     * 이메일 인증을 위한 사용자 찾기
     *
     * 사용자가 이메일을 통해 아이디를 찾을 때 사용
     * 해당 이메일과 일치하는 사용자 정보를 반환
     *
     * @param email 사용자가 입력한 이메일
     * @return Optional<User> 객체 (사용자가 존재하면 User, 없으면 empty)
     */
    Optional<User> findByEmailIgnoreCase(String email);
}
