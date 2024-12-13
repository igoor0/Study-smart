package uni.studysmart.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PreferenceDTO {
    private Long id;
    private Long dayId;
    private String dayName;
    private List<String> times;
    private List<List<String>> timeRanges;
    private Long studentId;
    private Long courseId;

    public PreferenceDTO(Long id, Long dayId, String dayName, List<List<String>> lists, Long aLong, Long aLong1) {
        this.id = id;
        this.dayId = dayId;
        this.dayName = dayName;
        this.times = new ArrayList<>();
        this.timeRanges = new ArrayList<>();
        this.studentId = aLong;
        this.courseId = aLong1;
    }

    public PreferenceDTO(Long dayId, String dayName, List<String> times, List<List<String>> timeRanges, Long studentId, Long courseId) {
        this.dayId = dayId;
        this.dayName = dayName;
        this.times = times;
        this.timeRanges = timeRanges;
        this.studentId = studentId;
        this.courseId = courseId;
    }
}