package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * BookmarkedBuilding 엔티티 클래스
 *
 * 사용자가 즐겨찾기한 건물 정보를 저장하는 테이블 (bookmarked_buildings)과 매핑
 * 특정 건물(building)과 사용자(User) 간의 관계 설정
 * 건물 즐겨찾기 등록 시간 포함
 */
@Entity
@Table(name = "bookmarked_buildings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkedBuilding {

    /**
     * 즐겨찾기한 사용자 ID (Foreign Key)
     *
     * User 테이블의 id(users.id)와 연결
     * ManyToOne 관계 설정 (한 사용자가 여러 건물을 즐겨찾기 가능)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_BOOKMARK_USER"))
    private User user;

    /**
     * 즐겨찾기한 건물 ID (Foreign Key)
     *
     * Building 테이블의 building_id(buildings.building_id)와 연결
     * ManyToOne 관계 설정 (한 건물이 여러 사용자에게 즐겨찾기 될 수 있음)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false, foreignKey = @ForeignKey(name = "FK_BOOKMARK_BUILDING"))
    private Building building;

    /**
     * 건물 즐겨찾기 등록 시간 (자동 설정)
     *
     * 사용자가 건물을 즐겨찾기에 추가한 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    /**
     * 복합 키 (user_id, building_id) 설정
     *
     * 동일한 사용자가 동일한 건물을 중복 즐겨찾기하지 않도록 방지
     */
    @Embeddable
    @Data
    public static class BookmarkedBuildingId implements java.io.Serializable {
        private Long user;
        private Long building;
    }

    @EmbeddedId
    private BookmarkedBuildingId id;
}
