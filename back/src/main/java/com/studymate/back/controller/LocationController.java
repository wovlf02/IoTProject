package com.studymate.back.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
//@RequestMapping("/api/location")
public class LocationController {

    @GetMapping("/current")
    public ResponseEntity<Map<String, Double>> getCurrentLocation() {
        Map<String, Double> location = new HashMap<>();
        location.put("latitude", 37.5665);  // 기본 좌표 (서울)
        location.put("longitude", 126.9780);

        return ResponseEntity.ok(location);  // JSON 응답 보장
    }
}
