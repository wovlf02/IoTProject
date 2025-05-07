package com.smartcampus.back.repository;

import com.smartcampus.back.entity.TimetableSlot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimetableSlotRepository extends JpaRepository<TimetableSlot, Long> {

    /**
     * 시간표 ID로 해당 시간표의 모든 교시 조회
     *
     * @param timetableId 시간표 ID
     * @return 교시 목록
     */
    List<TimetableSlot> findByTimetableId(Long timetableId);

    /**
     * 시간표 ID로 해당 시간표의 교시 전체 삭제
     *
     * @param timetableId 시간표 ID
     */
    void deleteByTimetableId(Long timetableId);
}
