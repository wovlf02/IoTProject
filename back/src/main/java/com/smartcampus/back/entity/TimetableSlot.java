package com.smartcampus.back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "timetable_slots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimetableSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "timetable_slot_seq_gen")
    @SequenceGenerator(name = "timetable_slot_seq_gen", sequenceName = "timetable_slot_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timetable_id", nullable = false)
    private Timetable timetable;

    @Column(name = "day_of_week", nullable = false, length = 10)
    private String dayOfWeek; // MON, TUE, WED, ...

    @Column(name = "period", nullable = false)
    private Integer period;

    @Column(name = "subject_name", nullable = false, length = 255)
    private String subjectName;

    @Column(name = "professor", length = 100)
    private String professor;

    @Column(name = "building", length = 100)
    private String building;

    @Column(name = "room", length = 100)
    private String room;

    @Column(name = "start_time", nullable = false, length = 10)
    private String startTime; // 예: "09:00"

    @Column(name = "end_time", nullable = false, length = 10)
    private String endTime;

    @Column(name = "week_type", length = 20)
    private String weekType; // ALL / ODD / EVEN 등

    @Column(name = "memo", length = 500)
    private String memo;
}
