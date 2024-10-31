package uni.studysmart.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private Lecturer lecturer;

    @ManyToMany(mappedBy = "courses")
    private List<Group> groups;
}

