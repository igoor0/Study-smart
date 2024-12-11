package uni.studysmart.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final ZonedDateTime timestamp;
    private final String errorCode;

    public ApiException(String message, HttpStatus httpStatus, ZonedDateTime timestamp, String errorCode) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
        this.errorCode = errorCode;
    }
}
