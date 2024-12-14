package uni.studysmart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uni.studysmart.model.user.Student;
import uni.studysmart.utils.TimeRange;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "preferences")
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long dayId;
    private String dayName;

    @ElementCollection
    @CollectionTable(name = "preference_time_ranges", joinColumns = @JoinColumn(name = "preference_id"))
    @Column(name = "time_range")
    private List<TimeRange> timeRanges;

    @ElementCollection
    @CollectionTable(name = "availability_times", joinColumns = @JoinColumn(name = "availability_id"))
    @Column(name = "time")
    private List<String> times;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}