package uni.studysmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.dto.AvailabilityDTO;
import uni.studysmart.model.Availability;
import uni.studysmart.request.AvailabilityRequest;
import uni.studysmart.service.AvailabilityService;

import java.util.List;

@RestController
@RequestMapping("/api/availabilities")
public class AvailabilityController {

    @Autowired
    private AvailabilityService availabilityService;

    @GetMapping
    public ResponseEntity<List<AvailabilityDTO>> getAvailability() {
        return availabilityService.getAllAvailabilities();
    }
    @PostMapping("/add")
    public ResponseEntity<String> addAvailability(@RequestBody AvailabilityRequest availabilityRequest) {
        try {
            availabilityService.addAvailability(availabilityRequest);
            return ResponseEntity.ok("Dostępność dodana pomyślnie");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Błąd podczas dodawania dostępności: " + e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Availability> getAvailabilityById(@PathVariable Long id) {
        return availabilityService.getAvailabilityById(id);
    }
    @DeleteMapping("/id")
    public ResponseEntity deleteAvailability(@PathVariable Long id) {
        return ResponseEntity.ok(availabilityService.deleteAvailability(id));
    }
}

