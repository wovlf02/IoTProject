package com.studymate.back;

import com.studymate.back.entity.Schedule;
import com.studymate.back.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ScheduleRepositoryTest {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Test
    void testFindAllSchedules() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertNotNull(scheduleList);
        scheduleList.forEach(System.out::println);
    }

    @Test
    @Rollback(false)
    void testInsertSchedule() {
        Schedule schedule = new Schedule();
        schedule.setLectureName("데이터베이스");
        schedule.setProfessorName("김교수");
        schedule.setLectureRoom("IT관 204호");
        schedule.setLectureTime(LocalTime.of(10, 30));
        schedule.setLectureDay("화요일");

        Schedule saved = scheduleRepository.save(schedule);
        assertNotNull(saved.getScheduleId());
        System.out.println("삽입된 데이터: " + saved);
    }

    @Test
    @Rollback(false)
    void testUpdateSchedule() {
        Long idToUpdate = 23L; // 업데이트할 ID를 여기에 지정
        Optional<Schedule> optional = scheduleRepository.findById(idToUpdate);

        assertTrue(optional.isPresent(), "해당 ID로 데이터를 찾을 수 없습니다.");

        Schedule schedule = optional.get();
        schedule.setLectureRoom("집");
        schedule.setProfessorName("이백범 교수");

        Schedule updated = scheduleRepository.save(schedule);
        System.out.println("업데이트된 데이터: " + updated);
    }

    @Test
    @Rollback(false)
    void testDeleteSchedule() {
        Long idToDelete = 23L; // 삭제할 ID를 여기에 지정

        boolean existsBefore = scheduleRepository.existsById(idToDelete);
        assertTrue(existsBefore, "삭제 전: 데이터가 존재하지 않습니다.");

        scheduleRepository.deleteById(idToDelete);

        boolean existsAfter = scheduleRepository.existsById(idToDelete);
        assertFalse(existsAfter, "삭제 후: 데이터가 아직 존재합니다.");
        System.out.println("삭제 완료: ID = " + idToDelete);
    }
}
