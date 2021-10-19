package engine.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;



    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
    }

    public void register(User user) {
        userRepository.save(user);
    }

    public boolean userExists(User user) {
        return userRepository.existsById(user.getEmail());
    }
}
