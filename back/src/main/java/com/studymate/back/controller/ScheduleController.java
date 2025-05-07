package com.studymate.back.controller;

import com.studymate.back.entity.Schedule;
import com.studymate.back.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleRepository scheduleRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Schedule>> getUserSchedules(@PathVariable Long userId) {
        List<Schedule> schedules = scheduleRepository.findByUserUserId(userId);
        if(schedules.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(schedules);
    }
}
