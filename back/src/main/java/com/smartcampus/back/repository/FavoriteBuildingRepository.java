package com.smartcampus.back.repository;

import com.smartcampus.back.entity.FavoriteBuilding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteBuildingRepository extends JpaRepository<FavoriteBuilding, Long> {

    /**
     * 특정 사용자의 즐겨찾기 건물 목록 조회
     *
     * @param userId 사용자 ID
     * @return 즐겨찾기 건물 리스트
     */
    List<FavoriteBuilding> findByUserId(Long userId);

    /**
     * 사용자가 특정 건물을 즐겨찾기에 등록했는지 여부 확인
     *
     * @param userId 사용자 ID
     * @param buildingId 건물 ID
     * @return 등록 여부
     */
    boolean existsByUserIdAndBuildingId(Long userId, Long buildingId);
}
