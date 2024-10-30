package uni.studysmart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
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

    public static void main(String[] args) {SpringApplication.run(StudySmartApplication.class, args);
    }

    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("POST", "GET", "PUT", "DELETE").allowedOrigins("*");
            }
        };
    }
}
