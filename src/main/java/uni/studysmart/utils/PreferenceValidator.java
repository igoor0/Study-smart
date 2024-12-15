package uni.studysmart.utils;

import org.springframework.stereotype.Component;
import uni.studysmart.dto.AvailabilityDTO;
import uni.studysmart.dto.PreferenceDTO;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.TimeRange;

import java.util.List;

@Component
public class PreferenceValidator {

    public boolean validatePreferenceWithAvailability(PreferenceDTO preference, AvailabilityDTO availability) {
        try {
            List<TimeRange> preferenceRanges = TimeRangeParser.parseTimeRanges(preference.getTimeRanges());
            List<TimeRange> availabilityRanges = TimeRangeParser.parseTimeRanges(availability.getTimeRanges());

            return preferenceRanges.stream()
                    .allMatch(preferenceRange ->
                            availabilityRanges.stream()
                                    .anyMatch(availabilityRange -> timeRangesOverlap(preferenceRange, availabilityRange))
                    );
        } catch (ApiRequestException e) {
            System.err.println("Error while parsing time ranges: " + e.getMessage());
            return false;
        }
    }
    private static boolean timeRangesOverlap(TimeRange preferenceRange, TimeRange availabilityRange) {
        return !preferenceRange.getEndTime().isBefore(availabilityRange.getStartTime()) &&
                !preferenceRange.getStartTime().isAfter(availabilityRange.getEndTime());
    }
}