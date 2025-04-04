package com.studymate.back.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @Column(name = "lecture_name", nullable = false)
    private String lectureName;

    @Column(name = "professor_name", nullable = false)
    private String professorName;

    @Column(name = "lecture_room", nullable = false)
    private String lectureRoom;

    @Column(name = "lecture_time", nullable = false)
    private LocalTime lectureTime; // 시간만 저장

    @Column(name = "lecture_day", nullable = false)
    private String lectureDay;

    @Override
    public String toString() {
        return "Schedule{" +
                "scheduleId=" + scheduleId +
                ", lectureName='" + lectureName + '\'' +
                ", professorName='" + professorName + '\'' +
                ", lectureRoom='" + lectureRoom + '\'' +
                ", lectureTime=" + lectureTime +
                ", lectureDay='" + lectureDay + '\'' +
                '}';
    }
}
