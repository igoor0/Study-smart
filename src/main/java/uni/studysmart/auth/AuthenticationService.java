package uni.studysmart.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uni.studysmart.config.JwtService;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.user.Lecturer;
import uni.studysmart.model.Role;
import uni.studysmart.model.user.Planner;
import uni.studysmart.model.user.Student;
import uni.studysmart.model.user.User;
import uni.studysmart.repository.LecturerRepository;
import uni.studysmart.repository.StudentRepository;
import uni.studysmart.repository.UserRepository;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;

    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, LecturerRepository lecturerRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
    }

    private boolean doesUserExist(String username) {
        return userRepository.findByEmail(username).isPresent();
    }

    public AuthenticationResponse register(RegisterRequest request) {

        if (doesUserExist(request.getEmail())) {
            throw new ApiRequestException("User with email " + request.getEmail() + " already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.STUDENT);
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(
                user,
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().toString());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerPlanner(PlannerRegisterRequest request) {

        if (doesUserExist(request.getEmail())) {
            throw new ApiRequestException("User with email " + request.getEmail() + " already exists");
        }

        Planner planner = new Planner();
        planner.setEmail(request.getEmail());
        planner.setFirstName(request.getFirstName());
        planner.setLastName(request.getLastName());
        planner.setPassword(passwordEncoder.encode(request.getPassword()));
        planner.setRole(Role.PLANNER);
        userRepository.save(planner);

        var jwtToken = jwtService.generateToken(
                planner,
                planner.getId(),
                planner.getFirstName(),
                planner.getLastName(),
                planner.getRole().toString());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerLecturer(LecturerRegisterRequest request) {

        if (doesUserExist(request.getEmail())) {
            throw new ApiRequestException("User with email " + request.getEmail() + " already exists");
        }

        Lecturer lecturer = new Lecturer();
        lecturer.setEmail(request.getEmail());
        lecturer.setFirstName(request.getFirstName());
        lecturer.setLastName(request.getLastName());
        lecturer.setPassword(passwordEncoder.encode(request.getPassword()));
        lecturer.setClassRoom(request.getClassRoom());
        lecturer.setDepartment(request.getDepartment());
        lecturer.setOfficeNumber(request.getOfficeNumber());
        lecturer.setRole(Role.LECTURER);
        lecturer.setConfirmed(false);
        lecturerRepository.save(lecturer);


        var jwtToken = jwtService.generateToken(
                lecturer,
                lecturer.getId(),
                lecturer.getFirstName(),
                lecturer.getLastName(),
                lecturer.getRole().toString());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse registerStudent(StudentRegisterRequest request) {
        if (doesUserExist(request.getEmail())) {
            throw new ApiRequestException("User with email " + request.getEmail() + " already exists");
        }

        Student student = new Student();
        student.setEmail(request.getEmail());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setPassword(passwordEncoder.encode(request.getPassword()));
        student.setRole(Role.STUDENT);
        student.setIndexNumber(request.getIndexNumber());
        student.setMajor(request.getMajor());
        studentRepository.save(student);


        var jwtToken = jwtService.generateToken(
                student,
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getRole().toString());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiRequestException("User with the email " + request.getEmail() + " not found"));
        var jwtToken = jwtService.generateToken(
                user,
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().toString()
        );
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        var user = userRepository.findByEmail(changePasswordRequest.getEmail())
                .orElseThrow(() -> new ApiRequestException("User with the email " + changePasswordRequest.getEmail() + " not found"));
        if (passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(
                    user,
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole().toString());
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new ApiRequestException("Old password is incorrect");
        }
    }

}
