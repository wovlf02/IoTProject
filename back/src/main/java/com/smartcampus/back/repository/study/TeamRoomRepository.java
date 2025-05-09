package com.smartcampus.back.repository.study;

import com.smartcampus.back.entity.study.TeamRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRoomRepository extends JpaRepository<TeamRoom, Long> {
}
