package com.smartcampus.back.repository;

import com.smartcampus.back.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    /**
     * 건물 코드로 건물 단건 조회
     *
     * @param buildingCode 건물 코드
     * @return 건물 정보
     */
    Optional<Building> findByBuildingCode(String buildingCode);
}
