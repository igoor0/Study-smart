package uni.studysmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.studysmart.model.Availability;
import uni.studysmart.model.Group;
import uni.studysmart.model.Preference;
import uni.studysmart.model.Schedule;
import uni.studysmart.repository.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    /**
     * Dopasowuje optymalny czas na zajęcia na podstawie dostępności wykładowcy
     * oraz preferencji studentów.
     */
    public List<LocalTime> getBestMatchingSchedule(Long lecturerId, Long courseId, Long groupId) {
        // Pobieramy dostępność wykładowcy
        List<Availability> availabilities = availabilityRepository.findByLecturerId(lecturerId);
        // Pobieramy preferencje studentów
        List<Preference> preferences = preferenceRepository.findByStudent_GroupIdAndCourseId(groupId, courseId);

        // Mapa przechowująca liczbę głosów dla każdego okna czasowego
        Map<Availability, Integer> timeVotes = new HashMap<>();

        // Obliczanie liczby głosów dla każdego okna czasowego
        for (Availability availability : availabilities) {
            int voteCount = 0;
            for (Preference preference : preferences) {
                if (preference.getDayOfWeek().equals(availability.getDayOfWeek()) &&
                        !preference.getStartTime().isBefore(availability.getStartTime()) &&
                        !preference.getEndTime().isAfter(availability.getEndTime())) {
                    voteCount++;
                }
            }
            timeVotes.put(availability, voteCount);
        }

        // Sortujemy dostępności według liczby głosów i wybieramy najlepsze
        return timeVotes.entrySet().stream()
                .sorted(Map.Entry.<Availability, Integer>comparingByValue().reversed())
                .limit(6) // Zakładamy 6 spotkań w semestrze
                .map(entry -> entry.getKey().getStartTime())
                .collect(Collectors.toList());
    }
    public void saveSchedule(Long lecturerId, Long courseId, Long groupId) {
        List<LocalTime> bestTimes = getBestMatchingSchedule(lecturerId, courseId, groupId);

        for (LocalTime time : bestTimes) {
            Schedule schedule = new Schedule();
            schedule.setDate(LocalDate.now()); // Ustaw datę zajęć
            schedule.setStartTime(time);
            schedule.setEndTime(time.plusHours(2)); // Przykładowa długość zajęć (2 godziny)
            schedule.setGroup(groupRepository.findById(groupId)
                    .orElseThrow(() -> new IllegalArgumentException("Grupa nie istnieje")));
            schedule.setCourse(courseRepository.findById(courseId)
                    .orElseThrow(() -> new IllegalArgumentException("Kurs nie istnieje")));
            scheduleRepository.save(schedule);
        }
    }
}
