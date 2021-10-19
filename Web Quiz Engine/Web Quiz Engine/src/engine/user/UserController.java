package engine.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;



    @PostMapping("/api/register")
    public void register(@Valid @RequestBody User user) {
        if(userService.userExists(user)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Email already exists"
            );

        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.register(user);
    }
}
