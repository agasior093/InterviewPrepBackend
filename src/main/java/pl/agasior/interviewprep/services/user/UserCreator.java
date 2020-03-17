package pl.agasior.interviewprep.services.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.configuration.security.oauth2.FacebookOAuth2UserInfo;
import pl.agasior.interviewprep.configuration.security.oauth2.OAuth2UserInfo;
import pl.agasior.interviewprep.dto.SignUpRequest;
import pl.agasior.interviewprep.entities.AuthProvider;
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

    public User createUser(OAuth2UserInfo userInfo) {
        userValidator.validate(userInfo);
        var user = buildUser(userInfo);
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

    private User buildUser(OAuth2UserInfo userInfo) {
        return User.builder()
                .username(userInfo.getEmail())
                .email(userInfo.getEmail())
                .password(encoder.encode(userInfo.getEmail()))
                .roles(Set.of(Role.User))
                .isActive(true)
                .provider(designateAuthProvider(userInfo))
                .imageUrl(userInfo.getImageUrl())
                .build();
    }

    private AuthProvider designateAuthProvider(OAuth2UserInfo userInfo) {
        if(userInfo instanceof FacebookOAuth2UserInfo) return AuthProvider.Facebook;
        return AuthProvider.Local;
    }
}
