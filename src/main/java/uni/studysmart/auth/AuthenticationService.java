package uni.studysmart.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uni.studysmart.config.JwtService;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.Role;
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
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new ApiRequestException("User with email " + request.getEmail() + " already exists");
        }else {
            userRepository.save(user);
        }
        var jwtToken = jwtService.generateToken(user);
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
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new ApiRequestException("User with the email " + request.getEmail() + " not found"));
        var jwtToken = jwtService.generateToken(user);
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        var user = userRepository.findByEmail(changePasswordRequest.getEmail()).orElseThrow(() -> new ApiRequestException("User with the email " + changePasswordRequest.getEmail() + " not found"));
        if(passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        }else {
            throw new ApiRequestException("Old password is incorrect");
        }
    }
}
