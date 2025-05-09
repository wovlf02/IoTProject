package com.smartcampus.back.controller.study;

import com.smartcampus.back.dto.study.TeamRoomCreateRequest;
import com.smartcampus.back.dto.study.TeamRoomResponse;
import com.smartcampus.back.service.study.TeamRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/study/team/rooms")
public class TeamRoomController {

    @Autowired
    private TeamRoomService teamRoomService;

    @PostMapping("/create")
    public TeamRoomResponse create(@RequestBody TeamRoomCreateRequest request) {
        return teamRoomService.createTeamRoom(request);
    }

    @GetMapping("/{id}")
    public TeamRoomResponse getById(@PathVariable Long id) {
        return teamRoomService.getTeamRoomById(id);
    }

    @GetMapping // 🔥 새로 추가!
    public List<TeamRoomResponse> getAllRooms() {
        return teamRoomService.getAllTeamRooms();
    }
}
