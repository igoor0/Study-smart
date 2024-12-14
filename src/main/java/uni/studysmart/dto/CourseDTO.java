package uni.studysmart.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class CourseDTO {
    private Long id;
    private String name;
    private String description;
    private int courseDuration;
    private Long lecturerId;
    private boolean scheduled;


    public CourseDTO(String name, String description, int courseDuration) {
        this.name = name;
        this.description = description;
        this.scheduled = false;
        this.courseDuration = courseDuration;
    }

    public CourseDTO(String name, String description, int courseDuration, long lecturerId) {
        this.name = name;
        this.description = description;
        this.scheduled = false;
        this.courseDuration = courseDuration;
        this.lecturerId = lecturerId;
    }

    public CourseDTO(String name, String description, int courseDuration, long lecturerId, boolean scheduled) {
        this.name = name;
        this.description = description;
        this.scheduled = scheduled;
        this.courseDuration = courseDuration;
        this.lecturerId = lecturerId;
    }

    public CourseDTO(Long id, String name, String description, int courseDuration, Long lecturerId, boolean scheduled) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.courseDuration = courseDuration;
        this.lecturerId = lecturerId;
        this.scheduled = scheduled;
    }
}
