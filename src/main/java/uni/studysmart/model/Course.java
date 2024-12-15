package uni.studysmart.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uni.studysmart.model.user.Lecturer;

import java.time.LocalTime;

@Getter
@Setter
@Data
@Entity(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Boolean isScheduled = false;
    private Integer courseDuration;

    @ManyToOne
    private Lecturer lecturer;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

}