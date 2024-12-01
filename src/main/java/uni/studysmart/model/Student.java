package uni.studysmart.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "students")
public class Student extends User {
    private String indexNumber;
    private String major;
    @ManyToOne
    private Group group;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Preference> preferences;
}
