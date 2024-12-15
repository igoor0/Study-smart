package uni.studysmart.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.utils.TimeRangeParser;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeRange {
    private LocalTime startTime;
    private LocalTime endTime;

    public List<TimeRange> parseTimeRanges(List<List<String>> timeRanges) {
        return TimeRangeParser.parseTimeRanges(timeRanges);
    }

    public List<List<String>> convertTimeRangesToString(List<TimeRange> timeRanges) {
        return timeRanges.stream()
                .map(timeRange -> List.of(timeRange.getStartTime().toString(), timeRange.getEndTime().toString()))
                .collect(Collectors.toList());
    }
}
