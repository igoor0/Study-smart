package uni.studysmart.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.ServletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("UTC")),
                "API_REQUEST_ERROR"
        );
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException e) {
        HttpStatus internalServerError = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiException apiException = new ApiException(
                e.getMessage(),
                internalServerError,
                ZonedDateTime.now(ZoneId.of("UTC")),
                "NULL_POINTER_EXCEPTION"
        );
        return new ResponseEntity<>(apiException, internalServerError);
    }

    @ExceptionHandler(value = {ServletException.class})
    public ResponseEntity<Object> handleServletException(ServletException e) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("UTC")),
                "SERVLET EXCEPTION"

        );
        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity<Object> handleIOException(IOException e) {
        HttpStatus httpStatus = HttpStatus.NOT_ACCEPTABLE;
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("UTC")),
                "IOEXCEPTION"

        );
        return new ResponseEntity<>(apiException, httpStatus);
    }
    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException e) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiException apiException = new ApiException(
                e.getMessage(),
                httpStatus,
                ZonedDateTime.now(ZoneId.of("UTC")),
                "JWT_EXPIRED"

        );
        return new ResponseEntity<>(apiException, httpStatus);
    }


}
