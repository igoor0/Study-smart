package uni.studysmart.model;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "students")
public class Student extends User {
    private String indexNumber;
    private String major;
    @ManyToOne
    private Group group;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Preference> preferences;
}
