package uni.studysmart.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uni.studysmart.model.user.Lecturer;

import java.time.LocalTime;

@Getter
@Setter
@Data
@Entity(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean isScheduled = false; // Czy dany kurs jest już zaplanowany (posiada już wybrane godziny w tygodniu)
    private Integer courseDuration;
    private LocalTime startTime;  // Godzina rozpoczęcia danego kursu
    private LocalTime endTime;  // Godzina zakończenia danego kursu

    @ManyToOne
    private Lecturer lecturer;

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