package uni.studysmart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "preferences")
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}