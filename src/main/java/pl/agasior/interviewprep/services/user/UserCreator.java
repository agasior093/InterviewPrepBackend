package pl.agasior.interviewprep.services.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.SignUpRequest;
import pl.agasior.interviewprep.entities.Role;
import pl.agasior.interviewprep.entities.User;
import pl.agasior.interviewprep.repositories.UserRepository;

import java.util.Set;

@Service
public class UserCreator {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder encoder;

    UserCreator(final UserRepository userRepository, final UserValidator userValidator, final PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.encoder = encoder;
    }

    public User createUser(SignUpRequest signUpRequest) {
        userValidator.validate(signUpRequest);
        final var user = buildUser(signUpRequest);
        return userRepository.save(user);
    }

    private User buildUser(SignUpRequest registerUserDto) {
        return User.builder()
                .username(registerUserDto.getUsername())
                .password(encoder.encode(registerUserDto.getPassword()))
                .email(registerUserDto.getEmail())
                .isActive(true)
                .roles(Set.of(Role.User))
                .build();
    }
}
