package com.studymate.back.repository;

import com.studymate.back.entity.Location;
import com.studymate.back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 위치 데이터를 관리하는 Repository
 * 데이터 저장, 조회 기능 제공
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    /**
     * 특정 사용자의 현재 위치 데이터를 조회하는 메서드
     * @param user 유저 정보
     * @return 최신순으로 정렬하여 반환
     */
    List<Location> findByUserOrderByCreatedAtDesc(User user);
}
