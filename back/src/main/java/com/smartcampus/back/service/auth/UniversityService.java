package com.smartcampus.back.service.auth;

import com.smartcampus.back.entity.auth.University;
import com.smartcampus.back.repository.auth.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniversityService {

    private final UniversityRepository universityRepository;

    /**
     * 학교 이름 검색
     * @param keyword 검색어
     * @return 해당 검색어를 포함하는 학교 목록
     */
    public List<University> searchUniversities(String keyword) {
        return universityRepository.findByNameContainingIgnoreCase(keyword);
    }
}