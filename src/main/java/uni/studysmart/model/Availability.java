package uni.studysmart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uni.studysmart.model.user.Lecturer;

import java.time.DayOfWeek;

import uni.studysmart.utils.TimeRange;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "availabilities")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @ElementCollection
    @CollectionTable(name = "availability_time_ranges", joinColumns = @JoinColumn(name = "availability_id"))
    @Column(name = "time_range")
    private List<TimeRange> timeRanges;

    @ManyToOne
    private Lecturer lecturer;
}


