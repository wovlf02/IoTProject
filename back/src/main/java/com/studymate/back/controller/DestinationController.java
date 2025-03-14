package com.studymate.back.controller;

import com.studymate.back.entity.Destination;
import com.studymate.back.repository.DestinationRepository;
import com.studymate.back.service.DestinationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin(origins = "http://10.0.2.2:8080/api")
@RestController
@RequestMapping("/api/destinations")
public class DestinationController {
    private final DestinationRepository destinationRepository;

    public DestinationController(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchDestinations(@RequestParam String name) {
        // 목적지 검색 로직

        return ResponseEntity.ok(destinationRepository.findByName(name));
    }
}