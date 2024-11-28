package uni.studysmart.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name="student_groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "group")
    private List<Student> students;

    @ManyToOne
    private Course courses;
}

