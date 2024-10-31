package uni.studysmart.controller;

import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import uni.studysmart.model.Preference;
import uni.studysmart.service.PreferenceService;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    @PostMapping
    public ResponseEntity<String> addPreference(@RequestBody Preference preference,
                                                @RequestParam Long studentId) {
        preferenceService.addPreference(preference, studentId);
        return ResponseEntity.ok("Preferencja dodana pomyślnie");
    }
}
