package uni.studysmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.studysmart.repository.LecturerRepository;

@Service
public class LecturerService {
    @Autowired
    private LecturerRepository lecturerRepository;
}
