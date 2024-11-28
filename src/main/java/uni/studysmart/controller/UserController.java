package uni.studysmart.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.dto.UserDTO;
import uni.studysmart.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@EnableMethodSecurity(prePostEnabled = true)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{user}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long user) {
        return ResponseEntity.ok(userService.getUser(user));
    }
    @DeleteMapping("/{user}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteUser(@PathVariable Long user) {
        return ResponseEntity.ok(userService.deleteUser(user));
    }

}
