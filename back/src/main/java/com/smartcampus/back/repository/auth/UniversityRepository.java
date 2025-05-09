package com.smartcampus.back.repository.auth;

import com.smartcampus.back.entity.auth.University;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 학교 정보를 관리하는 JPA 리포지토리입니다.
 */
public interface UniversityRepository extends JpaRepository<University, Long> {

    /**
     * 학교 이름으로 검색
     */
    List<University> findByNameContainingIgnoreCase(String name);
}