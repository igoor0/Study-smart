package uni.studysmart.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name="student_groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "group")
    private List<Student> students;

    @ManyToMany
    private List<Course> courses;
}

