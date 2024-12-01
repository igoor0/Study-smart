package uni.studysmart.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.model.Preference;
import uni.studysmart.request.PreferenceDTO;
import uni.studysmart.service.PreferenceService;

import java.util.List;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    @GetMapping
    public ResponseEntity<List<Preference>> getAllPreferences() {
        return preferenceService.getAllPreferences();
    }

    @PostMapping
    public ResponseEntity<String> addPreference(@RequestBody PreferenceDTO preference) {
        preferenceService.addPreference(preference);
        return ResponseEntity.ok("Preferencja dodana pomyślnie");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Preference> getPreferenceById(@PathVariable Long id) {
        return preferenceService.getPreferenceById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePreferenceById(@PathVariable Long id) {
        return ResponseEntity.ok(preferenceService.deletePreferenceById(id));
    }
}
