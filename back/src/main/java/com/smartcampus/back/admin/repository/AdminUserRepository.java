package com.smartcampus.back.admin.repository;

import com.smartcampus.back.auth.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 관리자 전용 사용자 조회 레포지토리
 * - 사용자 전체 목록 조회
 * - 사용자 상태(활성/정지 등)로 필터링
 */
@Repository
public interface AdminUserRepository extends JpaRepository<User, Long> {

    /**
     * 전체 사용자 목록을 페이징 형태로 조회
     * 상태 필터링도 가능 (e.g., ACTIVE, SUSPENDED)
     *
     * @param status 사용자 상태 (null이면 전체 조회)
     * @param pageable 페이징 정보
     * @return 조건에 맞는 사용자 페이지
     */
    @Query("SELECT u FROM User u WHERE (:status IS NULL OR u.status = :status)")
    Page<User> findUsersByStatus(@Param("status") String status, Pageable pageable);
}
