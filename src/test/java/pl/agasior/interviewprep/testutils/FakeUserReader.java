package pl.agasior.interviewprep.testutils;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.agasior.interviewprep.dto.UserDto;
import pl.agasior.interviewprep.entities.Role;
import pl.agasior.interviewprep.services.user.AuthenticationFacade;

import java.util.Optional;
import java.util.Set;

@Service
@Profile("test")
public class FakeUserReader implements AuthenticationFacade {
    private static final UserDto DEFAULT_USER = UserDto.builder().username("test").email("test@test.pl").roles(Set.of(Role.User)).build();
    private UserDto customUser;

    public void provideUser(UserDto customUser) {
        this.customUser = customUser;
    }

    @Override
    public Optional<UserDto> getLoggedUser() {
        return customUser != null ? Optional.of(customUser) : Optional.of(DEFAULT_USER);
    }
}
