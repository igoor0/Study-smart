package uni.studysmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.service.ScheduleService;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    /**
     * Endpoint do uzyskiwania najlepszego harmonogramu zajęć dla danej grupy, kursu i wykładowcy.
     *
     * @param lecturerId ID wykładowcy
     * @param courseId   ID kursu
     * @param groupId    ID grupy
     * @return Lista najlepszych godzin zajęć
     */
    @GetMapping("/best")
    public ResponseEntity<List<LocalTime>> getBestSchedule(
            @RequestParam Long lecturerId,
            @RequestParam Long courseId,
            @RequestParam Long groupId) {

        List<LocalTime> bestTimes = scheduleService.getBestMatchingSchedule(lecturerId, courseId, groupId);
        return ResponseEntity.ok(bestTimes);
    }

    /**
     * Endpoint do zapisywania harmonogramu zajęć dla danej grupy, kursu i wykładowcy.
     *
     * @param lecturerId ID wykładowcy
     * @param courseId   ID kursu
     * @param groupId    ID grupy
     * @return Status operacji
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveSchedule(
            @RequestParam Long lecturerId,
            @RequestParam Long courseId,
            @RequestParam Long groupId) {

        scheduleService.saveSchedule(lecturerId, courseId, groupId);
        return ResponseEntity.ok("Harmonogram zapisany pomyślnie");
    }
}

