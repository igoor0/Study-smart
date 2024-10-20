package uni.studysmart.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uni.studysmart.model.User;
import uni.studysmart.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
            return userRepository.findAll();
    }
    public User getUser(Long id) {
            return userRepository.findById(id).orElse(null);
    }
}
