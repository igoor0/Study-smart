package uni.studysmart.utils;

import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.TimeRange;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TimeRangeParser {

    public static List<TimeRange> parseTimeRanges(List<List<String>> timeRanges) {
        List<TimeRange> parsedTimeRanges = new ArrayList<>();

        for (List<String> range : timeRanges) {
            if (range.isEmpty()) {
                throw new ApiRequestException("Time range cannot be empty.");
            }

            // Pierwszy czas w zakresie traktujemy jako start
            LocalTime startTime = LocalTime.parse(range.get(0));

            LocalTime endTime;

            if (range.size() == 1) {
                // Jeśli jest tylko jedna godzina, traktujemy ją jako start i end time
                endTime = startTime;
            } else if (range.size() == 2) {
                // Jeśli są dokładnie dwa elementy, drugi traktujemy jako end time
                endTime = LocalTime.parse(range.get(1));
            } else {
                // W przypadku więcej niż dwóch elementów, traktujemy je jako kolejne start/end range'y
                throw new ApiRequestException("Invalid time range format: Each range must contain at most two times.");
            }

            // Dodajemy utworzony przedział czasowy
            parsedTimeRanges.add(new TimeRange(startTime, endTime));
        }

        return parsedTimeRanges;
    }

    public static List<List<String>> convertTimeRangesToString(List<TimeRange> timeRanges) {
        return timeRanges.stream()
                .map(timeRange -> List.of(timeRange.getStartTime().toString(), timeRange.getEndTime().toString()))
                .collect(Collectors.toList());
    }
}
