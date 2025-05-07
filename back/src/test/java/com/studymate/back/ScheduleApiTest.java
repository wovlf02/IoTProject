package com.studymate.back;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studymate.back.entity.Schedule;
import com.studymate.back.entity.User;
import com.studymate.back.repository.ScheduleRepository;
import com.studymate.back.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class ScheduleApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    private Long testUserId;

    @BeforeEach
    @Rollback(false)
    public void setup() {
        // 사용자 생성
        User user = new User();
        user.setUsername("테스트 사용자");
        user = userRepository.save(user);
        testUserId = user.getUserId();

        // 시간표 데이터 삽입
        Schedule schedule = new Schedule();
        schedule.setLectureName("자료구조");
        schedule.setProfessorName("김교수");
        schedule.setLectureRoom("IT관 101호");
        schedule.setStartTime("09:00");
        schedule.setEndTime("10:30");
        schedule.setLectureDay("월요일");
        schedule.setUser(user);

        scheduleRepository.save(schedule);
    }

    @Test
    public void testGetScheduleByUserId() throws Exception {
        mockMvc.perform(get("/api/schedule/user/" + testUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // 기대하는 schedule 수
                .andExpect(jsonPath("$[0].lectureName").value("자료구조"));
    }
}
