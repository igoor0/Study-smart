package uni.studysmart.service;

import org.springframework.stereotype.Service;
import uni.studysmart.model.Lecturer;
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
}
