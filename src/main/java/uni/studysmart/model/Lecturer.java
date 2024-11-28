package uni.studysmart.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "lecturers")
public class Lecturer extends User {

    @OneToMany(mappedBy = "lecturer")
    private List<Course> courses;

    @OneToMany(mappedBy = "lecturer", cascade = CascadeType.ALL)
    private List<Availability> availabilities;
}
