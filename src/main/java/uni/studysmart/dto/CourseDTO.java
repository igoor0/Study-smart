package uni.studysmart.dto;

import lombok.*;
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
    private List<Long> groupsIdList;
    private Long lecturerId;

    // Konstruktor, gettery i settery
}
