package uni.studysmart.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uni.studysmart.dto.UserDTO;
import uni.studysmart.exception.ApiRequestException;
import uni.studysmart.model.user.User;
import uni.studysmart.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("User not found"));
        return mapToUserDTO(user);
    }

    private UserDTO mapToUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .build();
    }

    public ResponseEntity deleteUser(Long user) {
        Optional<User> user1 = userRepository.findById(user);
        if (user1.isPresent()) {
            userRepository.deleteById(user);
        }
        return ResponseEntity.ok(user1.orElseThrow(() -> new ApiRequestException("User not found")));
    }
}
