package uni.studysmart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.studysmart.model.Lecturer;
import uni.studysmart.repository.LecturerRepository;

@Service
@RequiredArgsConstructor
public class LecturerService {
    @Autowired
    private LecturerRepository lecturerRepository;

    public Lecturer saveLecturer(Lecturer lecturer) {
        return lecturerRepository.save(lecturer);
    }
}
