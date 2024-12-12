package uni.studysmart.model;

import jakarta.persistence.*;
import lombok.*;
import uni.studysmart.model.user.Student;

import java.util.List;

@Entity(name="student_groups")
@Getter
@Setter
@RequiredArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "group")
    private List<Student> students;
}

