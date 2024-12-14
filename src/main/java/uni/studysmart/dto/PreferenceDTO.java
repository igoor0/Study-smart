package uni.studysmart.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PreferenceDTO {
    private Long id;
    private Long dayId;
    private String dayName;
    private List<List<String>> timeRanges;
    private List<String> times;
    private Long courseId;
    private Long studentId;

    public PreferenceDTO(Long id, Long dayId, String dayName, List<List<String>> timeRanges, List<String> times, Long courseId, Long studentId)  {
        this.id = id;
        this.dayId = dayId;
        this.dayName = dayName;
        this.timeRanges = timeRanges;
        this.times = times;
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public PreferenceDTO(Long dayId, String dayName, List<List<String>> timeRanges, List<String> times, Long courseId, Long studentId) {
        this.dayId = dayId;
        this.dayName = dayName;
        this.timeRanges = timeRanges;
        this.times = times;
        this.courseId = courseId;
        this.studentId = studentId;
    }
}