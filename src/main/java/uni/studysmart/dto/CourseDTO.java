package uni.studysmart.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private String name;
    private boolean scheduled;
    private int courseDuration;
    private String startTime;
    private String endTime;
    private Long lecturerId;

    public CourseDTO(String name, boolean scheduled, int courseDuration, String startTime, String endTime, long lecturerId) {
        this.name = name;
        this.scheduled = scheduled;
        this.courseDuration = courseDuration;
        this.startTime = startTime;
        this.endTime = endTime;
        this.lecturerId = lecturerId;
    }
    public CourseDTO(String name, boolean scheduled, int courseDuration, String startTime, String endTime) {
        this.name = name;
        this.scheduled = scheduled;
        this.courseDuration = courseDuration;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
