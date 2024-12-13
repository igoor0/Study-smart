package uni.studysmart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.dto.PreferenceDTO;
import uni.studysmart.service.PreferenceService;

import java.util.List;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    private final PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @GetMapping
    public ResponseEntity<List<PreferenceDTO>> getAllPreferences() {
        List<PreferenceDTO> preferenceDTOList = preferenceService.getAllPreferences();
        return ResponseEntity.ok(preferenceDTOList);
    }

    @PostMapping
    public ResponseEntity<List<Long>> createPreferences(@RequestBody List<PreferenceDTO> preferenceDTOList) {
        List<Long> createdIdList = preferenceService.addPreferences(preferenceDTOList);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIdList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreferenceDTO> getPreferenceById(@PathVariable Long id) {
        PreferenceDTO preferenceDTO = preferenceService.getPreferenceById(id);
        return ResponseEntity.ok(preferenceDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreferenceById(@PathVariable Long id) {
        preferenceService.deletePreferenceById(id);
        return ResponseEntity.noContent().build();
    }
}
