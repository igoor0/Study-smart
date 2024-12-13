package uni.studysmart.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityDTO {
    private Long id;
    private int iden; // Dzień tygodnia jako liczba
    private String dayName; // Nazwa dnia tygodnia
    private List<List<String>> timeRanges; // Zakresy czasowe w formacie [["07:00", "09:00"], ["14:00", "16:00"]]
    private Long lecturerId;

    public AvailabilityDTO(int iden, String dayName, List<List<String>> timeRanges, Long lecturerId) {
        this.iden = iden;
        this.dayName = dayName;
        this.timeRanges = timeRanges;
        this.lecturerId = lecturerId;
    }
}