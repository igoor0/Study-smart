package uni.studysmart.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.studysmart.model.Availability;
import uni.studysmart.model.Preference;
import uni.studysmart.repository.AvailabilityRepository;
import uni.studysmart.repository.PreferenceRepository;

import java.util.List;

@Service
public class Utils {
    @Autowired
    private AvailabilityRepository availabilityRepository;
    @Autowired
    private PreferenceRepository preferenceRepository;

    public void deleteAllPreferencesConflictsAvailability(Availability availability) {
        List<Preference> preferenceList = preferenceRepository.findAll();
        for (Preference preference : preferenceList) {
            if(preference.getStartTime().isBefore(availability.getEndTime()) || preference.getEndTime().isAfter(availability.getStartTime()))
            {
                preferenceRepository.delete(preference);
            }
        }
    }


}
