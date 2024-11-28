package uni.studysmart.request;

import jakarta.persistence.*;
import lombok.Data;
import uni.studysmart.model.Group;
import uni.studysmart.model.Lecturer;

import java.util.List;

@Data
public class CourseRequest {
    private String name;
    private int courseDuration;
    private String lecturerId;
    private String groupId;
}
