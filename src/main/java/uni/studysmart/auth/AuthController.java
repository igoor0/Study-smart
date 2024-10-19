package uni.studysmart.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.model.Manager;
import uni.studysmart.model.Role;
import uni.studysmart.repository.ManagerRepository;
import uni.studysmart.repository.RoleRepository;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private RoleRepository roleRepository;
    private ManagerRepository managerRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, ManagerRepository managerRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.managerRepository = managerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody Login login){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login.getUsername(),
                        login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Manager login success", HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody Register register) {
        if (managerRepository.existsByUsername(register.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        Manager manager = new Manager();
        manager.setUsername(register.getUsername());
        manager.setPassword(passwordEncoder.encode((register.getPassword())));
        Role roles = roleRepository.findByName("MANAGER").get();
        manager.setRoles(Collections.singletonList(roles));

        managerRepository.save(manager);

        return new ResponseEntity<>("Manager registration success!", HttpStatus.OK);
    }
}
