package pl.agasior.interviewprep.services.user;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.UserDto;
import pl.agasior.interviewprep.entities.User;
import pl.agasior.interviewprep.repositories.UserRepository;

import java.util.Optional;

@Service
@Profile({"dev", "prod"})
public class UserReader implements AuthenticationFacade {

    private final UserRepository userRepository;

    public UserReader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserDto> getLoggedUser() {
        final var authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName()).map(this::toDto);
    }

    private UserDto toDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }
}
