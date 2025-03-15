package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * Building 엔티티 클래스
 *
 * 사용자가 등록하는 건물 정보를 저장하는 테이블 (buildings)과 매핑
 * 건물 이름, 층수, 호실(강의실, 연구실 등) 정보 포함
 * 건물 등록 시간 저장
 */
@Entity
@Table(name = "buildings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Building {

    /**
     * 건물 고유 ID (Primary Key)
     *
     * 자동 증가 (IDENTITY 전략)
     * 각 건물을 식별하는 고유 값
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    private Long buildingId;

    /**
     * 건물 이름
     *
     * 최대 200자까지 저장 가능
     * NULL 불가능 (반드시 이름이 있어야 함)
     */
    @Column(name = "building_name", nullable = false, length = 200)
    private String buildingName;

    /**
     * 건물 층수
     *
     * 건물의 층 정보를 저장
     * 최대 200자까지 저장 가능
     * NULL 불가능
     */
    @Column(name = "building_floor", nullable = false, length = 200)
    private String buildingFloor;

    /**
     * 건물 호실 및 특정 공간 정보
     *
     * 강의실, 연구실, 특정 공간 등의 정보를 저장
     * 최대 200자까지 저장 가능
     * NULL 불가능
     */
    @Column(name = "building_room", nullable = false, length = 200)
    private String buildingRoom;

    /**
     * 건물 등록 시간 (자동 설정)
     *
     * 사용자가 건물 정보를 등록한 시각을 저장
     * 데이터베이스에서 자동으로 현재 시간 삽입
     */
    @CreationTimestamp
    @Column(name = "building_created_at", nullable = false)
    private Timestamp createdAt;
}
