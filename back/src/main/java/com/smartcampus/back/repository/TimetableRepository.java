package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    /**
     * 특정 사용자의 시간표 조회
     *
     * @param ownerId 사용자 ID
     * @return 사용자에 해당하는 시간표
     */
    Optional<Timetable> findByOwnerId(Long ownerId);
}
