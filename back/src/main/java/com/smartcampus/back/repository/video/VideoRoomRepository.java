package com.smartcampus.back.repository.video;

import com.smartcampus.back.entity.video.VideoRoom;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface VideoRoomRepository extends JpaRepository<VideoRoom, Long> {
    List<VideoRoom> findByTeamId(Long teamId);
}
