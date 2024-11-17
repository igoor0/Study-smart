package uni.studysmart.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uni.studysmart.config.JwtService;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.Role;
import uni.studysmart.model.Student;
import uni.studysmart.model.Lecturer;
import uni.studysmart.model.User;
import uni.studysmart.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        validateEmailForRole(request.getEmail());

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ApiRequestException("User with email " + request.getEmail() + " already exists");
        }

        // Tworzymy użytkownika na podstawie roli określonej przez e-mail
        User user;
        if (request.getEmail().endsWith("@student.umg.pl")) {
            user = new Student();
            user.setRole(Role.STUDENT);
        } else if (request.getEmail().endsWith("@wykladowca.umg.pl")) {
            user = new Lecturer();
            user.setRole(Role.LECTURER);
        } else if (request.getEmail().endsWith("@planner.umg.pl")) {
            user = new User();
            user.setRole(Role.PLANNER);
        } else if (request.getEmail().endsWith("@admin.umg.pl")) {
            user = new User();
            user.setRole(Role.ADMIN);
        } else {
            throw new ApiRequestException("Invalid email domain specified");
        }

        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstname());
        user.setLastName(request.getLastname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Zapis użytkownika do bazy danych
        userRepository.save(user);

        // Generowanie tokenu JWT
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

    private void validateEmailForRole(String email) {
        if (!email.endsWith("@student.umg.pl") && !email.endsWith("@wykladowca.umg.pl") && !email.endsWith("@planner.umg.pl") && !email.endsWith("@admin.umg.pl")) {
            throw new ApiRequestException("Mail domain has to end with either: student/wykladowca/planista @umg.pl");
        }
    }
}
