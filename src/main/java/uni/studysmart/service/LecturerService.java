package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.user.Lecturer;
import uni.studysmart.repository.LecturerRepository;

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;

    public LecturerService(LecturerRepository lecturerRepository) {
        this.lecturerRepository = lecturerRepository;
    }

    public Lecturer saveLecturer(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }

    public Long enableLecturer(Long lecturerId) {
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new ApiRequestException("Lecturer not found"));
        lecturer.setConfirmed(true);
        lecturerRepository.save(lecturer);
        return lecturer.getId();
    }
}
