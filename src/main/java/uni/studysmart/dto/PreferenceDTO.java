package uni.studysmart.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PreferenceDTO {
    private Long id;
    private String dayOfWeek;
    private String startTime;
    private String endTime;
    private Long studentId;
    private Long courseId;

    public PreferenceDTO(String dayOfWeek, String startTime, String endTime, long studentId, long courseId) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.studentId = studentId;
        this.courseId = courseId;
    }
}