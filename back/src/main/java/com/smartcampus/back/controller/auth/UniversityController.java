package com.smartcampus.back.controller.auth;

import com.smartcampus.back.entity.auth.University;
import com.smartcampus.back.service.auth.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/universities")
public class UniversityController {

    private final UniversityService universityService;

    /**
     * 학교 검색 API
     * @param keyword 검색어
     * @return 검색된 학교 목록
     */
    @GetMapping("/search")
    public List<University> searchUniversities(@RequestParam String keyword) {
        return universityService.searchUniversities(keyword);
    }
}