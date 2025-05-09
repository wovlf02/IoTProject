package com.smartcampus.back.repository.auth;

import com.smartcampus.back.entity.auth.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 학교 정보를 관리하는 JPA 리포지토리입니다.
 */
public interface UniversityRepository extends JpaRepository<University, Long> {

    /**
     * 학교 이름으로 포함 검색 (대소문자 무시)
     */
    List<University> findByNameContainingIgnoreCase(String name);

    /**
     * 학교 이름으로 정확히 검색 (대소문자 무시)
     */
    Optional<University> findByName(String name);
}
