package uni.studysmart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
 public class AdminController {

    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("GET:: admin controller");
    }
    @PostMapping
    public ResponseEntity<String> post() {
        return ResponseEntity.ok("POST:: admin controller");
    }
    @PutMapping
    public ResponseEntity<String> put() {
        return ResponseEntity.ok("PUT:: admin controller");
    }
    @DeleteMapping
    public ResponseEntity<String> delete() {
        return ResponseEntity.ok("DELETE:: admin controller");
    }
}
