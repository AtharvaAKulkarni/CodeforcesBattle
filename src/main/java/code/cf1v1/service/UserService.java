package code.cf1v1.service;

import code.cf1v1.entity.User;
import code.cf1v1.entity.VerifyUserEntity;
import code.cf1v1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

@Component
public class UserService {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;
    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity<?> register(User user) {

        if (verifyUser(user)) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User saved = userRepository.save(user);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        }
        return new ResponseEntity<>("Handle not on codeforces", HttpStatus.NOT_FOUND);
    }

    public boolean verifyUser(User user) throws HttpClientErrorException {
        String url = "https://codeforces.com/api/user.info?handles=<handle>&checkHistoricHandles=false";
        url = url.replace("<handle>", user.getUsername());
        try {
            VerifyUserEntity response =
                    restTemplate.getForObject(url, VerifyUserEntity.class);

            return response != null && "OK".equals(response.getStatus());

        } catch (HttpClientErrorException e) {
            // handle not found (400 from Codeforces)
            return false;
        }
    }
}
