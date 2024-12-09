package uni.studysmart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.dto.AvailabilityDTO;
import uni.studysmart.service.AvailabilityService;

import java.util.List;

@RestController
@RequestMapping("/api/availabilities")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping
    public ResponseEntity<List<AvailabilityDTO>> getAvailabilities() {
        List<AvailabilityDTO> availibilityDTOList = availabilityService.getAllAvailabilities();
        return ResponseEntity.ok(availibilityDTOList);
    }

    @PostMapping
    public ResponseEntity<Long> createAvailability(@RequestBody AvailabilityDTO availabilityDTO) {
        Long createdId = availabilityService.addAvailability(availabilityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityDTO> getAvailabilityById(@PathVariable Long id) {
        AvailabilityDTO availabilityDTO = availabilityService.getAvailabilityById(id);
        return ResponseEntity.ok(availabilityDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable Long id) {
        availabilityService.deleteAvailability(id);
        return ResponseEntity.noContent().build();
    }
}

