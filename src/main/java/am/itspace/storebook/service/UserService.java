package am.itspace.storebook.service;

import am.itspace.storebook.entity.User;
import am.itspace.storebook.entity.UserRole;
import am.itspace.storebook.exception.DublicateResourceException;
import am.itspace.storebook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    public void save(User user) throws DublicateResourceException, MessagingException {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DublicateResourceException("User has already existed!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(UserRole.USER);
        user.setEnable(false);
        user.setVerifyToken(UUID.randomUUID().toString());
        userRepository.save(user);

        mailService.sendHtmlEmail(user.getEmail(), "Please verify your email",
                "Hi " + user.getName() + "\n" +
                "Please verify your account by click on this link" +
                        " <a href=\"http://localhost:8080/user/verify?email=" + user.getEmail() + "&token=" + user.getVerifyToken() + "\">Activate</a>"
        );
    }

    public void verifyUser(String email, String token) throws Exception {
        Optional<User> userOptional = userRepository.findByEmailAndVerifyToken(email, token);
        if(userOptional.isEmpty()) {
            throw new Exception("User does not exists with email and token");
        }
        User user = userOptional.get();
        if(user.isEnable()) {
            throw new Exception("User has enabled");
        }
        user.setEnable(true);
        user.setVerifyToken(null);
        userRepository.save(user);
    }
}
