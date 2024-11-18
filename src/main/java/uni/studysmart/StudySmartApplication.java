package uni.studysmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import uni.studysmart.auth.RegisterRequest;
import uni.studysmart.model.User;
import uni.studysmart.repository.UserRepository;

@SpringBootApplication
public class StudySmartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudySmartApplication.class, args);
        //initializeUsers();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
//    private static void initializeUsers() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        RegisterRequest[] users = new RegisterRequest[]{
//                new RegisterRequest("admin", "admin", "admin@admin.umg.pl", "password"),
//                new RegisterRequest("Jan", "Kowalski", "jan.kowalski@wykladowca.umg.pl", "password"),
//                new RegisterRequest("Anna", "Nowak", "anna.nowak@student.umg.pl", "password"),
//                new RegisterRequest("Igor", "Nowak", "igor@student.umg.pl", "password"),
//                new RegisterRequest("Piotr", "Zieliński", "piotr.zielinski@planner.umg.pl", "password")
//        };
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        for (RegisterRequest user : users) {
//            HttpEntity<RegisterRequest> request = new HttpEntity<>(user, headers);
//            try {
//                restTemplate.exchange("http://localhost:8080/api/auth/register", HttpMethod.POST, request, String.class);
//                System.out.println("User " + user.getEmail() + " registered successfully.");
//            } catch (Exception e) {
//                System.out.println("Error registering user " + user.getEmail() + ": " + e.getMessage());
//            }
//        }
//    }
}