package uni.studysmart.dto;

import jakarta.persistence.*;
import lombok.*;
import uni.studysmart.model.Group;
import uni.studysmart.model.Lecturer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

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
    private List<GroupDTO> groups;
    private LecturerDTO lecturer;

    // Konstruktor, gettery i settery
}
