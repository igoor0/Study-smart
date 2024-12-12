package uni.studysmart.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.service.LecturerService;

@RequiredArgsConstructor
@RequestMapping("/api/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final LecturerService lecturerService;

    @PostMapping("/registerStudent")
    public ResponseEntity<AuthenticationResponse> registerStudent(@RequestBody StudentRegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerStudent(registerRequest));
    }

    @PostMapping("/registerLecturer")
    public ResponseEntity<AuthenticationResponse> registerLecturer(@RequestBody LecturerRegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerLecturer(registerRequest));
    }
    @PostMapping("/confirmLecturer/{id}")
    public ResponseEntity<Long> confirmLecturer(@PathVariable Long id) {
        Long confirmLecturerId = lecturerService.enableLecturer(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(confirmLecturerId);
    }

    @PostMapping("/addPlanner")
    public ResponseEntity<AuthenticationResponse> addPlanner(@RequestBody PlannerRegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationService.registerPlanner(registerRequest));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(loginRequest));
    }

    @PutMapping("/change-password")
    public ResponseEntity<AuthenticationResponse> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return ResponseEntity.ok(authenticationService.changePassword(changePasswordRequest));
    }

}
