package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.model.Preference;

import org.springframework.beans.factory.annotation.Autowired;
import uni.studysmart.model.Student;
import uni.studysmart.repository.PreferenceRepository;
import uni.studysmart.repository.StudentRepository;

@Service
public class PreferenceService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private StudentRepository studentRepository;

    public void addPreference(Preference preference, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono studenta o podanym ID"));
        preference.setStudent(student);
        preferenceRepository.save(preference);
    }
}

