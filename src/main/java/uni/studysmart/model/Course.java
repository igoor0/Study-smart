package uni.studysmart.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Data
@Entity(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean isScheduled; // Czy dany kurs jest już zaplanowany (posiada już wybrane godziny w tygodniu) TODO: czy jest to odpowiednie miejsce na taką zmienną?
    private Integer courseDuration;
    private LocalTime startTime;  // Godzina rozpoczęcia danego kursu
    private LocalTime endTime;  // Godzina zakończenia danego kursu

    @ManyToOne
    private Lecturer lecturer;

    @OneToMany(mappedBy = "courses")
    private List<Group> groups;
    //TODO: ADD COURSE CRUD LOGIC

    //Mamy dany kurs
    //Ten kurs jest prowadzony przez KILKU prowadzących (dany kurs może mieć wielu prowadzących)
    //Ten kurs ma wiele grup studentów (kilka grup studentów może mieć ten sam przedmiot)
    //Ten kurs ma określoną ilość godzin trwania, z założeniem na cały semestr
    //Dany kurs może wystąpić dla danej grupy tylko raz w tygodniu, przy założeniu, że trwa cały semestr (powiedzmy 15 tygodni)
    //

    //CO MOŻEMY ZROBIC:
    // - możemy dodać nowy kurs
    // - możemy usunąc kurs
    // - możemy
}